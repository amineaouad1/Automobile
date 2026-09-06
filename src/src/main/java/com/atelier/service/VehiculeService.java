package com.atelier.service;

import com.atelier.dto.CreerVehiculeRequest; // (awla l-chemin dyal DTO dyalek)
import com.atelier.dto.VehiculeDTO;
import com.atelier.entity.Client;
import com.atelier.entity.Vehicule;
import com.atelier.repository.ClientRepository;
import com.atelier.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final ClientRepository clientRepository;

    public List<VehiculeDTO> listerTous() {
        return vehiculeRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public VehiculeDTO getById(Long id) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule introuvable avec l'ID : " + id));
        return mapToDTO(vehicule);
    }

    public VehiculeDTO creer(CreerVehiculeRequest request) {
        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable avec l'ID : " + request.getClientId()));

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculationFictive(request.getImmatriculation()); // Hna beddelnaha
        vehicule.setMarque(request.getMarque());
        vehicule.setModele(request.getModele());
        vehicule.setAnnee(request.getAnnee());
        vehicule.setKilometrage(request.getKilometrage());
        vehicule.setClientFictif(client);

        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        return mapToDTO(savedVehicule);
    }

    private VehiculeDTO mapToDTO(Vehicule vehicule) {
        VehiculeDTO dto = new VehiculeDTO();
        dto.setId(vehicule.getId());
        dto.setImmatriculation(vehicule.getImmatriculationFictive()); // Hna 2ita t-beddel
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setAnnee(vehicule.getAnnee());
        dto.setKilometrage(vehicule.getKilometrage());
        if (vehicule.getClientFictif() != null) {
            dto.setClientId(vehicule.getClientFictif().getId());
        }
        return dto;
    }

    private VehiculeDTO mapToDTO(Vehicule vehicule) {
        VehiculeDTO dto = new VehiculeDTO();
        dto.setId(vehicule.getId());
        dto.setImmatriculation(vehicule.getImmatriculation());
        dto.setMarque(vehicule.getMarque());
        dto.setModele(vehicule.getModele());
        dto.setAnnee(vehicule.getAnnee());
        dto.setKilometrage(vehicule.getKilometrage());
        if (vehicule.getClientFictif() != null) {
            dto.setClientId(vehicule.getClientFictif().getId());
        }
        return dto;
    }
}