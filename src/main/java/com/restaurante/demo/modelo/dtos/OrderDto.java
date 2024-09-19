package com.restaurante.demo.modelo.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private String uuid;
    private LocalDateTime fechaHoraPedido;
    private int cantidad;
    private String informacionAdicional;
    private float subTotalSinIva;
    private float valorIva;
    private String document;
    private float totalIncluyendoIva;
    private boolean entregado;
    private LocalDateTime fechaHoraEntrega;
    private String comboUuid;
    private String cliente;
}