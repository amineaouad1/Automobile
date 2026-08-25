package com.atelier.dto.request;
import com.atelier.entity.enums.StatutIntervention;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ChangerStatutRequest {
    private StatutIntervention nouveauStatut;
    private String commentaire;
    private BigDecimal coutEstime;
}