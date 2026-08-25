package com.atelier.service;

import com.atelier.dto.MecanicienDTO;
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

    public Mecanicien getEntityById(Long id) {
        return mecanicienRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Mécanicien introuvable : id=" + id));
    }
}