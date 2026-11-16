package com.example.taller.inventarioapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientosCreateResponse {
    private Integer movimientoId;
    private Integer productoId;
    private String tipoMovimiento;
    private Integer stockAnterior;
    private Integer stockNuevo;
    private String mensaje;
}
