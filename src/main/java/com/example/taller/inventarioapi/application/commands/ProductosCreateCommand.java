package com.example.taller.inventarioapi.application.commands;

import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductosCreateCommand {
    @Size(max = 500)
    private String sku;
    @Size(max = 500)
    private String nombre;
    private String descripcion;
    private Integer categoriaId;
    private BigDecimal precioCompra;
    private BigDecimal precioVenta;
    private Integer stockMinimo;
    private Integer stockActual;
}
