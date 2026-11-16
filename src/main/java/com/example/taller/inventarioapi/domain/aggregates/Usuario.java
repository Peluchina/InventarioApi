package com.example.taller.inventarioapi.domain.aggregates;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    private Integer id;
    private String nombre;
    private String apellidos;
    private BigDecimal dni;
    private String direccion;
    private Integer rolId;
    private String email;
    private String contrasenia;
    private Integer estadoId;
}
