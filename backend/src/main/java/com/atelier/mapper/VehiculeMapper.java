package com.atelier.mapper;

import com.atelier.dto.VehiculeDTO;
import com.atelier.entity.Vehicule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "client.id", target = "clientId")
    VehiculeDTO toDto(Vehicule vehicule);

    @Mapping(source = "clientId", target = "client.id")
    Vehicule toEntity(VehiculeDTO vehiculeDTO);
}
