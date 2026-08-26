package com.atelier.controller;

import com.atelier.dto.InterventionDTO;
import com.atelier.dto.request.AffecterMecanicienRequest;
import com.atelier.dto.request.ChangerStatutRequest;
import com.atelier.dto.request.CreerInterventionRequest;
import com.atelier.entity.Utilisateur;
import com.atelier.entity.enums.RoleUtilisateur;
import com.atelier.service.InterventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/interventions")
@RequiredArgsConstructor
public class InterventionController {

    private final InterventionService interventionService;

    @PostMapping
    public ResponseEntity<InterventionDTO> creer(@RequestBody CreerInterventionRequest request) {
        // TODO Phase 5 : remplacer par l'utilisateur extrait du JWT (SecurityContextHolder)
        Utilisateur conseillerFictif = utilisateurFictifConseiller();
        return ResponseEntity.status(HttpStatus.CREATED).body(interventionService.creer(request, conseillerFictif));
    }

    @GetMapping
    public ResponseEntity<List<InterventionDTO>> listerTous() {
        return ResponseEntity.ok(interventionService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterventionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(interventionService.getById(id));
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<InterventionDTO> changerStatut(
            @PathVariable Long id,
            @RequestBody ChangerStatutRequest request) {
        // TODO Phase 5 : remplacer par l'utilisateur extrait du JWT
        Utilisateur auteurFictif = utilisateurFictifManager();
        return ResponseEntity.ok(interventionService.changerStatut(id, request, auteurFictif));
    }

    @PatchMapping("/{id}/mecanicien")
    public ResponseEntity<InterventionDTO> affecterMecanicien(
            @PathVariable Long id,
            @RequestBody AffecterMecanicienRequest request) {
        // TODO Phase 5 : action manager-only
        Utilisateur managerFictif = utilisateurFictifManager();
        return ResponseEntity.ok(interventionService.affecterMecanicien(id, request, managerFictif));
    }

    // ==================== SIMULATION UTILISATEUR ====================
    private Utilisateur utilisateurFictifConseiller() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(1L);
        utilisateur.setNom("Conseiller Test");
        utilisateur.setRole(RoleUtilisateur.ROLE_USER);
        return utilisateur;
    }

    private Utilisateur utilisateurFictifManager() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId(2L);
        utilisateur.setNom("Manager Test");
        utilisateur.setRole(RoleUtilisateur.ROLE_MANAGER);
        return utilisateur;
    }
}