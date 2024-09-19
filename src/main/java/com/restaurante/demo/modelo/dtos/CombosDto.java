package com.restaurante.demo.modelo.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CombosDto {

    private String  id;

    @NotEmpty(message = "El nombre del combo es obligatorio")
    private String nombreCombo;
    @NotEmpty(message = "El nombre de la categoria es obligatorio")
    private String categoria;
    @NotEmpty(message = "La descripcion del combo es obligatorio")
    private String descripcion;
    @NotNull(message = "El precio del combo es obligatorio")
    private float  precioAntesIva;
    @NotNull(message = "El estado del combo es obligatorio")
    private boolean  disponible;

}
