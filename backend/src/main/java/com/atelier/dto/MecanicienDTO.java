package com.atelier.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MecanicienDTO {
    private Long id;
    private String nom;
    private String specialite;
    private boolean disponible;
}