package com.atelier.service;

import com.atelier.dto.VehiculeDTO;
import com.atelier.dto.request.CreerVehiculeRequest;
import com.atelier.entity.Vehicule;
import com.atelier.mapper.VehiculeMapper;
import com.atelier.repository.VehiculeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    public VehiculeService(VehiculeRepository vehiculeRepository, VehiculeMapper vehiculeMapper) {
        this.vehiculeRepository = vehiculeRepository;
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
        vehicule.setClientFictif(request.getClientFictif());

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
                .orElseThrow(() -> new IllegalArgumentException("Véhicule introuvable avec l'ID : " + id));
        return vehiculeMapper.toDto(vehicule);
    }
}
