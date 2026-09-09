package com.atelier.dto;
import com.atelier.entity.enums.Priorite;
import com.atelier.entity.enums.StatutIntervention;
import com.atelier.entity.enums.TypeIntervention;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class InterventionDTO {
    private Long id;
    private VehiculeDTO vehicule;
    private MecanicienDTO mecanicien;
    private UtilisateurDTO createur;
    private TypeIntervention type;
    private String descriptionClient;
    private String diagnostic;
    private StatutIntervention statut;
    private Priorite priorite;
    private BigDecimal coutEstime;
    private LocalDateTime dateDepot;
    private LocalDateTime dateRestitutionPrevue;
    private LocalDateTime dateCloture;
}