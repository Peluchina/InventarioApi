package com.example.taller.inventarioapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
public class ProveedoresListResponse {
    private Integer id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoId;
}
