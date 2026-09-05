package com.atelier.dto;
import com.atelier.entity.enums.StatutIntervention;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class HistoriqueInterventionDTO {
    private Long id;
    private String auteurNom;
    private StatutIntervention ancienStatut;
    private StatutIntervention nouveauStatut;
    private String commentaire;
    private LocalDateTime date;
}