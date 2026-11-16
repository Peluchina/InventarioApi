package com.example.taller.inventarioapi.domain.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {
    private Integer id;
    private String nombre;
    private String descripcion;
}
