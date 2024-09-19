package com.restaurante.demo.repositories.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "pedidos")
public class OrderEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "idpedidos", columnDefinition = "BINARY(16)")
    private UUID uuid;

    @Column(name = "fecha_hora_pedido")
    private LocalDateTime fechaHoraPedido;

    @Column(name = "cantidad")
    private int cantidad;

    @Column(name = "informacion_adicional")
    private String informacionAdicional;

    @Column(name = "sub_total_sin_iva")
    private float subTotalSinIva;

    @Column(name = "valor_iva")
    private float valorIva;

    @Column(name = "total_incluyendo_iva")
    private float totalIncluyendoIva;

    @Column(name = "entregado")
    private boolean entregado;

    @Column(name = "fecha_hora_entrega", nullable = false)
    private LocalDateTime fechaHoraEntrega;

    @ManyToOne
    @JoinColumn(name = "combos_id_combo", columnDefinition = "BINARY(16)", nullable = false)
    private CombosEntity combo;

    @ManyToOne(targetEntity = ClientEntity.class)
    @JoinColumn(name = "clientes_id_clientes", nullable = false)
    private ClientEntity cliente;


}


