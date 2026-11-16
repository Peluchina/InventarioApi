package com.example.taller.inventarioapi.application.dtos;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductosGetResponse {
    private Integer id;
    private String sku;
    private String nombre;
    private String descripcion;
    private Integer categoriaId;
    private String categoria;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockMinimo;
    private Integer stockActual;
}
