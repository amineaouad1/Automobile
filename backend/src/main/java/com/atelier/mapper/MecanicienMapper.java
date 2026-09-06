package com.atelier.mapper;

import com.atelier.dto.MecanicienDTO;
import com.atelier.entity.Mecanicien;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MecanicienMapper {
    MecanicienDTO toDto(Mecanicien mecanicien);
}