package com.example.taller.inventarioapi.domain.aggregates;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientoInventario {
    private Integer id;
    private Integer productoId;
    private String tipoMovimiento;
    private Integer cantidad;
    private Integer stockAnterior;
    private Integer stockNuevo;
    private String referencia;
    private Integer usuarioId;
    private LocalTime fecha;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    // Datos relacionados incluidos por los procedimientos de consulta.
    private String producto;
    private String sku;
    private String usuario;
}
