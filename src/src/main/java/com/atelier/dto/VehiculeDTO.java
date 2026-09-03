package com.atelier.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VehiculeDTO {
    private Long id;
    private String immatriculation;
    private String marque;
    private String modele;
    private int annee;
    private int kilometrage;
    private String clientId;
}