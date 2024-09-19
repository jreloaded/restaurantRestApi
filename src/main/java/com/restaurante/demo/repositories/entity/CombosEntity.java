package com.restaurante.demo.repositories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "combos")
public class CombosEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "idCombo", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "nombre_combo")
    private String nombreCombo;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "precio_antes_iva")
    private float precioAntesIva;

    @Column(name = "disponible")
    private boolean disponible;
}
