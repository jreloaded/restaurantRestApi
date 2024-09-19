package com.restaurante.demo.modelo.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDto  {

    private Integer idClientes;

    @NotEmpty(message = "El documento es obligatorio")
    private String document;
    @NotEmpty(message = "El Nombre es obligatorio")
    private String name;
    @NotEmpty(message = "El email es obligatorio")
    private String email;
    @NotEmpty(message = "El celular es obligatorio")
    private String phone;
    @NotEmpty(message = "La direccion es obligatorio")
    private String address;


}
