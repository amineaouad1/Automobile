package com.atelier.service;

import com.atelier.dto.VehiculeDTO;
import com.atelier.dto.request.CreerVehiculeRequest;
import com.atelier.entity.Vehicule;
import com.atelier.exception.ReglesMetierException;
import com.atelier.mapper.VehiculeMapper;
import com.atelier.repository.VehiculeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final VehiculeMapper vehiculeMapper;

    @Transactional
    public VehiculeDTO creer(CreerVehiculeRequest request) {
        if (vehiculeRepository.existsByImmatriculationFictive(request.getImmatriculationFictive())) {
            throw new ReglesMetierException("Immatriculation déjà existante : " + request.getImmatriculationFictive());
        }

        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculationFictive(request.getImmatriculationFictive());
        vehicule.setMarque(request.getMarque());
        vehicule.setModele(request.getModele());
        vehicule.setAnnee(request.getAnnee());
        vehicule.setKilometrage(request.getKilometrage());
        vehicule.setClientFictif(request.getClientFictif());

        return vehiculeMapper.toDto(vehiculeRepository.save(vehicule));
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
                .orElseThrow(() -> new ReglesMetierException("Véhicule introuvable : id=" + id));
        return vehiculeMapper.toDto(vehicule);
    }
}