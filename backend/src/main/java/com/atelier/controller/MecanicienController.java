package com.atelier.controller;

import com.atelier.dto.MecanicienDTO;
import com.atelier.dto.request.CreerMecanicienRequest;
import com.atelier.dto.request.DisponibiliteRequest;
import com.atelier.service.MecanicienService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<MecanicienDTO> creer(@RequestBody CreerMecanicienRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mecanicienService.creer(request));
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PatchMapping("/{id}/disponibilite")
    public ResponseEntity<MecanicienDTO> changerDisponibilite(
            @PathVariable Long id, @RequestBody DisponibiliteRequest request) {
        return ResponseEntity.ok(mecanicienService.changerDisponibilite(id, request.isDisponible()));
    }
}
