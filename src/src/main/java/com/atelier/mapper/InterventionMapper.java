package com.atelier.mapper;

import com.atelier.dto.InterventionDTO;
import com.atelier.entity.Intervention;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InterventionMapper {

    InterventionDTO toDto(Intervention intervention);

    Intervention toEntity(InterventionDTO interventionDTO);
}