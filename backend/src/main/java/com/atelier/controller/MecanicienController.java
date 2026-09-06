package com.atelier.controller;

import com.atelier.dto.MecanicienDTO;
import com.atelier.service.MecanicienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/mecaniciens")
@RequiredArgsConstructor
public class MecanicienController {

    private final MecanicienService mecanicienService;

    @GetMapping
    public ResponseEntity<List<MecanicienDTO>> listerTous() {
        return ResponseEntity.ok(mecanicienService.listerTous());
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<MecanicienDTO>> listerDisponibles() {
        return ResponseEntity.ok(mecanicienService.listerDisponibles());
    }
}