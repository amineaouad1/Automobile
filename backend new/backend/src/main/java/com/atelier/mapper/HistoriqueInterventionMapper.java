package com.atelier.mapper;

import com.atelier.dto.HistoriqueInterventionDTO;
import com.atelier.entity.HistoriqueIntervention;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HistoriqueInterventionMapper {
    @Mapping(source = "auteur.nom", target = "auteurNom")
    HistoriqueInterventionDTO toDto(HistoriqueIntervention historique);
}