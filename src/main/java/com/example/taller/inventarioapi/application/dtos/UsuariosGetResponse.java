package com.example.taller.inventarioapi.application.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UsuariosGetResponse {
    private Integer id;
    private String nombre;
    private String apellidos;
    private BigDecimal dni;
    private String direccion;
    private Integer rolId;
    private String email;
    private Integer estadoId;
}
