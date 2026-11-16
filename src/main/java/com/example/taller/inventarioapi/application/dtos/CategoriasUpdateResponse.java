package com.example.taller.inventarioapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriasUpdateResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
}
