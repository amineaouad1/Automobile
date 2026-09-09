package com.atelier.service;

import com.atelier.dto.MecanicienDTO;
import com.atelier.dto.request.CreerMecanicienRequest;
import com.atelier.entity.Mecanicien;
import com.atelier.mapper.MecanicienMapper;
import com.atelier.repository.MecanicienRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MecanicienService {

    private final MecanicienRepository mecanicienRepository;
    private final MecanicienMapper mecanicienMapper;

    @Transactional(readOnly = true)
    public List<MecanicienDTO> listerDisponibles() {
        return mecanicienRepository.findByDisponibleTrue().stream()
                .map(mecanicienMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MecanicienDTO> listerTous() {
        return mecanicienRepository.findAll().stream()
                .map(mecanicienMapper::toDto)
                .toList();
    }

    @Transactional
    public MecanicienDTO creer(CreerMecanicienRequest request) {
        Mecanicien mecanicien = new Mecanicien();
        mecanicien.setNom(request.getNom());
        mecanicien.setSpecialite(request.getSpecialite());
        mecanicien.setDisponible(true);
        return mecanicienMapper.toDto(mecanicienRepository.save(mecanicien));
    }

    @Transactional
    public MecanicienDTO changerDisponibilite(Long id, boolean disponible) {
        Mecanicien mecanicien = getEntityById(id);
        mecanicien.setDisponible(disponible);
        return mecanicienMapper.toDto(mecanicienRepository.save(mecanicien));
    }

    public Mecanicien getEntityById(Long id) {
        return mecanicienRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mécanicien introuvable : id=" + id));
    }
}
