package com.atelier.dto.request;

import lombok.Data;

@Data
public class CreerVehiculeRequest {
    private String immatriculation;
    private String marque;
    private String modele;
    private int annee;
    private int kilometrage;
    private Long clientId;
}