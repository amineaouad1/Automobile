package com.atelier.mapper;

import com.atelier.dto.VehiculeDTO;
import com.atelier.entity.Vehicule;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {
    VehiculeDTO toDto(Vehicule vehicule);
}