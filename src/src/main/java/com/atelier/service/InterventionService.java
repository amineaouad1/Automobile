package com.atelier.service;

import com.atelier.dto.InterventionDTO;
import com.atelier.dto.request.AffecterMecanicienRequest;
import com.atelier.dto.request.ChangerStatutRequest;
import com.atelier.dto.request.CreerInterventionRequest;
import com.atelier.entity.HistoriqueIntervention;
import com.atelier.entity.Intervention;
import com.atelier.entity.Mecanicien;
import com.atelier.entity.Utilisateur;
import com.atelier.entity.Vehicule;
import com.atelier.entity.enums.RoleUtilisateur;
import com.atelier.entity.enums.StatutIntervention;
import com.atelier.exception.AccesRefuseException;
import com.atelier.exception.ReglesMetierException;
import com.atelier.exception.TransitionInvalideException;
import com.atelier.mapper.InterventionMapper;
import com.atelier.repository.HistoriqueInterventionRepository;
import com.atelier.repository.InterventionRepository;
import com.atelier.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InterventionService {

    private final InterventionRepository interventionRepository;
    private final VehiculeRepository vehiculeRepository;
    private final MecanicienService mecanicienService;
    private final HistoriqueInterventionRepository historiqueRepository;
    private final InterventionMapper interventionMapper;

    // ==================== MACHINE À ÉTATS (RG-AUTO-04) ====================
    private static final Map<StatutIntervention, EnumSet<StatutIntervention>> TRANSITIONS_AUTORISEES =
            new EnumMap<>(StatutIntervention.class);

    static {
        TRANSITIONS_AUTORISEES.put(StatutIntervention.RECUE,
                EnumSet.of(StatutIntervention.DIAGNOSTIC_EN_COURS, StatutIntervention.ANNULEE));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.DIAGNOSTIC_EN_COURS,
                EnumSet.of(StatutIntervention.DEVIS_A_VALIDER, StatutIntervention.ANNULEE));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.DEVIS_A_VALIDER,
                EnumSet.of(StatutIntervention.EN_REPARATION, StatutIntervention.ANNULEE));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.EN_REPARATION,
                EnumSet.of(StatutIntervention.TERMINEE));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.TERMINEE,
                EnumSet.of(StatutIntervention.RESTITUEE));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.RESTITUEE, EnumSet.noneOf(StatutIntervention.class));
        TRANSITIONS_AUTORISEES.put(StatutIntervention.ANNULEE, EnumSet.noneOf(StatutIntervention.class));
    }

    @Transactional
    public InterventionDTO creer(CreerInterventionRequest request, Utilisateur createur) {
        Vehicule vehicule = vehiculeRepository.findById(request.getVehiculeId())
                .orElseThrow(() -> new ReglesMetierException("Véhicule introuvable : id=" + request.getVehiculeId()));

        Intervention intervention = new Intervention();
        intervention.setVehicule(vehicule);
        intervention.setCreateur(createur);
        intervention.setType(request.getType());
        intervention.setDescriptionClient(request.getDescriptionClient());
        intervention.setPriorite(request.getPriorite() != null ? request.getPriorite() : intervention.getPriorite());
        intervention.setStatut(StatutIntervention.RECUE);

        Intervention sauvegardee = interventionRepository.save(intervention);
        enregistrerHistorique(sauvegardee, null, StatutIntervention.RECUE, "Création de l'intervention", createur);

        return interventionMapper.toDto(sauvegardee);
    }

    @Transactional
    public InterventionDTO changerStatut(Long interventionId, ChangerStatutRequest request, Utilisateur auteur) {
        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new ReglesMetierException("Intervention introuvable : id=" + interventionId));

        StatutIntervention statutActuel = intervention.getStatut();
        StatutIntervention statutCible = request.getNouveauStatut();

        EnumSet<StatutIntervention> transitionsPossibles = TRANSITIONS_AUTORISEES.get(statutActuel);
        if (transitionsPossibles == null || !transitionsPossibles.contains(statutCible)) {
            throw new TransitionInvalideException(
                    "Transition interdite : " + statutActuel + " -> " + statutCible);
        }

        if (statutCible == StatutIntervention.DEVIS_A_VALIDER && request.getCoutEstime() == null) {
            throw new ReglesMetierException("Le coût estimé est obligatoire avant le statut Devis à valider.");
        }
        if (statutCible == StatutIntervention.DEVIS_A_VALIDER) {
            intervention.setCoutEstime(request.getCoutEstime());
        }

        if (statutCible == StatutIntervention.EN_REPARATION && intervention.getMecanicien() == null) {
            throw new ReglesMetierException("Un mécanicien doit être affecté avant le passage en réparation.");
        }

        if (statutCible == StatutIntervention.RESTITUEE) {
            if (auteur.getRole() != RoleUtilisateur.ROLE_MANAGER) {
                throw new AccesRefuseException("Seul le responsable atelier peut restituer une intervention.");
            }
            intervention.setDateCloture(java.time.LocalDateTime.now());
        }

        intervention.setStatut(statutCible);
        Intervention sauvegardee = interventionRepository.save(intervention);
        enregistrerHistorique(sauvegardee, statutActuel, statutCible, request.getCommentaire(), auteur);

        return interventionMapper.toDto(sauvegardee);
    }

    @Transactional
    public InterventionDTO affecterMecanicien(Long interventionId, AffecterMecanicienRequest request, Utilisateur auteur) {
        if (auteur.getRole() != RoleUtilisateur.ROLE_MANAGER) {
            throw new AccesRefuseException("Seul le responsable atelier peut affecter un mécanicien.");
        }

        Intervention intervention = interventionRepository.findById(interventionId)
                .orElseThrow(() -> new ReglesMetierException("Intervention introuvable : id=" + interventionId));

        Mecanicien mecanicien = mecanicienService.getEntityById(request.getMecanicienId());

        if (!mecanicien.isDisponible()) {
            throw new ReglesMetierException("Le mécanicien " + mecanicien.getNom() + " n'est pas disponible.");
        }

        intervention.setMecanicien(mecanicien);
        return interventionMapper.toDto(interventionRepository.save(intervention));
    }

    @Transactional(readOnly = true)
    public List<InterventionDTO> listerTous() {
        return interventionRepository.findAll().stream()
                .map(interventionMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public InterventionDTO getById(Long id) {
        return interventionMapper.toDto(
                interventionRepository.findById(id)
                        .orElseThrow(() -> new ReglesMetierException("Intervention introuvable : id=" + id))
        );
    }

    private void enregistrerHistorique(Intervention intervention, StatutIntervention ancien,
                                       StatutIntervention nouveau, String commentaire, Utilisateur auteur) {
        HistoriqueIntervention historique = new HistoriqueIntervention();
        historique.setIntervention(intervention);
        historique.setAuteur(auteur);
        historique.setAncienStatut(ancien);
        historique.setNouveauStatut(nouveau);
        historique.setCommentaire(commentaire);
        historiqueRepository.save(historique);
    }
}