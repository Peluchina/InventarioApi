package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.MovimientosCreateResponse;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoRegistroResultado;

@Component
public class MovimientosCreateResponseMapping {
    public MovimientosCreateResponse toResponse(MovimientoRegistroResultado resultado) {
        return new MovimientosCreateResponse(
                resultado.movimientoId(), resultado.productoId(), resultado.tipoMovimiento(),
                resultado.stockAnterior(), resultado.stockNuevo(), resultado.mensaje());
    }
}
