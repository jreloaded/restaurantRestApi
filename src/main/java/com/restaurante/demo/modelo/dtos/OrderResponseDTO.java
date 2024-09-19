package com.restaurante.demo.modelo.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    @NotEmpty(message = "El uuid del combo es obligatorio")
    private String comboUuid;

    @NotEmpty(message = "El documento es obligatorio")
    private String document;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(1)
    private Integer quantity;

    private String additionalInformation;
}
