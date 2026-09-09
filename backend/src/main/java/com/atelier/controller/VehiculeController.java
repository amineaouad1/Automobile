package com.atelier.controller;

import com.atelier.dto.VehiculeDTO;
import com.atelier.dto.request.CreerVehiculeRequest;
import com.atelier.service.VehiculeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vehicules")
public class VehiculeController {

    private final VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @PostMapping
    public ResponseEntity<VehiculeDTO> creer(@RequestBody CreerVehiculeRequest request) {
        return ResponseEntity.ok(vehiculeService.creer(request));
    }

    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> listerTous() {
        return ResponseEntity.ok(vehiculeService.listerTous());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.getById(id));
    }
}