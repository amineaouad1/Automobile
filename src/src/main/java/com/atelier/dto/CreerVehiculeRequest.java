package com.atelier.dto;

import lombok.Data;

@Data
public class CreerVehiculeRequest {
    private String immatriculation;
    private String marque;
    private String modele;
    private Integer annee;
    private Integer kilometrage;
    private Long clientId;
}