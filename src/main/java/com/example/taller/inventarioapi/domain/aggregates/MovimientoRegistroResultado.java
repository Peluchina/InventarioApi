package com.example.taller.inventarioapi.domain.aggregates;

public record MovimientoRegistroResultado(
        Integer movimientoId,
        Integer productoId,
        String tipoMovimiento,
        Integer stockAnterior,
        Integer stockNuevo,
        String mensaje) {
}
