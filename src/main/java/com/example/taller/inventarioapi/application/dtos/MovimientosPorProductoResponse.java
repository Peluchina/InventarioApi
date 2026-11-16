package com.example.taller.inventarioapi.application.dtos;

import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientosPorProductoResponse {
    private Integer id;
    private Integer productoId;
    private String producto;
    private String tipoMovimiento;
    private Integer cantidad;
    private Integer stockAnterior;
    private Integer stockNuevo;
    private String referencia;
    private String usuario;
    private LocalTime fecha;
    private LocalDateTime createdAt;
}
