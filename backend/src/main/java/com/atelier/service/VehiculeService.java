package com.atelier.service;

import com.atelier.dto.VehiculeDTO;
import com.atelier.dto.request.CreerVehiculeRequest;
import com.atelier.entity.Utilisateur;
import com.atelier.entity.Vehicule;
import com.atelier.mapper.VehiculeMapper;
import com.atelier.repository.UtilisateurRepository;
import com.atelier.repository.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeService(VehiculeRepository vehiculeRepository, UtilisateurRepository utilisateurRepository, VehiculeMapper vehiculeMapper) {
        this.vehiculeRepository = vehiculeRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.vehiculeMapper = vehiculeMapper;
    }

    @Transactional
    public VehiculeDTO creer(CreerVehiculeRequest request) {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation(request.getImmatriculation());
        vehicule.setMarque(request.getMarque());
        vehicule.setModele(request.getModele());
        vehicule.setAnnee(request.getAnnee());
        vehicule.setKilometrage(request.getKilometrage());

        Utilisateur client = utilisateurRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'ID : " + request.getClientId()));

        vehicule.setClient(client);

        vehicule = vehiculeRepository.save(vehicule);
        return vehiculeMapper.toDto(vehicule);
    }

    @Transactional(readOnly = true)
    public List<VehiculeDTO> listerTous() {
        return vehiculeRepository.findAll().stream()
                .map(vehiculeMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public VehiculeDTO getById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable avec l'ID : " + id));
        return vehiculeMapper.toDto(vehicule);
    }
}