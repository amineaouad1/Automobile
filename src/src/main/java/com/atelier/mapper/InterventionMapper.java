package com.atelier.mapper;

import com.atelier.dto.InterventionDTO;
import com.atelier.entity.Intervention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {VehiculeMapper.class, MecanicienMapper.class})
public interface InterventionMapper {
    InterventionDTO toDto(Intervention intervention);
}