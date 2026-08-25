package com.atelier.dto.request;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreerVehiculeRequest {
    private String immatriculationFictive;
    private String marque;
    private String modele;
    private int annee;
    private int kilometrage;
    private String clientFictif;
}