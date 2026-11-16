package com.example.taller.inventarioapi.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter 
@AllArgsConstructor 
public class UsuarioOAuth2 {
    private Integer id;
    private String nombre;
    private String apellidos;
    private String email;
    private Integer rolId;
    private String rol;
    private Integer estadoId;
}
