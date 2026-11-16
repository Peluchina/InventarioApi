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
public class Producto {
    private Integer id;
    private String sku;
    private String nombre;
    private String descripcion;
    private Integer categoriaId;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockMinimo;
    private Integer stockActual;
    private Integer estadoId;

    // Datos calculados o relacionados que devuelven los procedimientos de lectura.
    private String categoria;
    private String estadoStock;
}
