package com.atelier.controller;

import com.atelier.dto.InterventionDTO;
import com.atelier.dto.request.AffecterMecanicienRequest;
import com.atelier.dto.request.ChangerStatutRequest;
import com.atelier.dto.request.CreerInterventionRequest;
import com.atelier.entity.Utilisateur;
import com.atelier.security.UtilisateurPrincipal;
import com.atelier.service.InterventionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/interventions")
@RequiredArgsConstructor
public class InterventionController {

    private final InterventionService interventionService;

    @PostMapping
    public ResponseEntity<InterventionDTO> creer(
            @RequestBody CreerInterventionRequest request,
            @AuthenticationPrincipal UtilisateurPrincipal principal) {
        Utilisateur createur = principal.getUtilisateur();
        InterventionDTO creee = interventionService.creer(request, createur);
        return ResponseEntity.status(HttpStatus.CREATED).body(creee);
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
            @RequestBody ChangerStatutRequest request,
            @AuthenticationPrincipal UtilisateurPrincipal principal) {
        Utilisateur auteur = principal.getUtilisateur();
        InterventionDTO miseAJour = interventionService.changerStatut(id, request, auteur);
        return ResponseEntity.ok(miseAJour);
    }

    @PreAuthorize("hasRole('MANAGER')")
    @PatchMapping("/{id}/mecanicien")
    public ResponseEntity<InterventionDTO> affecterMecanicien(
            @PathVariable Long id,
            @RequestBody AffecterMecanicienRequest request,
            @AuthenticationPrincipal UtilisateurPrincipal principal) {
        Utilisateur manager = principal.getUtilisateur();
        InterventionDTO miseAJour = interventionService.affecterMecanicien(id, request, manager);
        return ResponseEntity.ok(miseAJour);
    }
}