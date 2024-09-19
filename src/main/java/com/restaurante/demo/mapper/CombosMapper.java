package com.restaurante.demo.mapper;

import com.restaurante.demo.modelo.dtos.CombosDto;
import com.restaurante.demo.repositories.entity.CombosEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CombosMapper {
    public CombosEntity toEntity(CombosDto combosDto) {
        if (combosDto == null) {
            return null;
        }

        CombosEntity combosEntity = new CombosEntity();

        if (combosDto.getId() != null && !combosDto.getId().isEmpty()) {
            combosEntity.setId(UUID.fromString(combosDto.getId()));
        }

        combosEntity.setNombreCombo(combosDto.getNombreCombo());
        combosEntity.setCategoria(combosDto.getCategoria());
        combosEntity.setDescripcion(combosDto.getDescripcion());
        combosEntity.setPrecioAntesIva(combosDto.getPrecioAntesIva());
        combosEntity.setDisponible(combosDto.isDisponible());

        return combosEntity;
    }

    public CombosDto toDTO(CombosEntity combosEntity) {
        if (combosEntity == null) {
            return null;
        }
        CombosDto combosDto = new CombosDto();

        combosDto.setId(combosEntity.getId() != null ? combosEntity.getId().toString() : null);
        combosDto.setNombreCombo(combosEntity.getNombreCombo());
        combosDto.setCategoria(combosEntity.getCategoria());
        combosDto.setDescripcion(combosEntity.getDescripcion());
        combosDto.setPrecioAntesIva(combosEntity.getPrecioAntesIva());
        combosDto.setDisponible(combosEntity.isDisponible());
        return combosDto;
    }


}
