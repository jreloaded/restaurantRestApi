package com.restaurante.demo.repositories.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
@Builder
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_clientes;

    @Column(name = "numero_documento")
    private String document;

    @Column(name = "nombre_apellido")
    private String name;

    /*@Column(name = "email")*/
    private String email;

    @Column(name = "numero_celular")
    private String phone;

    @Column(name = "direccion_envio")
    private String address;
}