package com.atelier.dto.request;
import com.atelier.entity.enums.Priorite;
import com.atelier.entity.enums.TypeIntervention;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreerInterventionRequest {
    private Long vehiculeId;
    private TypeIntervention type;
    private String descriptionClient;
    private Priorite priorite;
}