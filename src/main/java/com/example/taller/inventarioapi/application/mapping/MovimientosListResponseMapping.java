package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.MovimientosListResponse;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;

@Component
public class MovimientosListResponseMapping {
    public MovimientosListResponse toResponse(MovimientoInventario movimiento) {
        return new MovimientosListResponse(
                movimiento.getId(), movimiento.getProductoId(), movimiento.getProducto(), movimiento.getSku(),
                movimiento.getTipoMovimiento(), movimiento.getCantidad(), movimiento.getStockAnterior(),
                movimiento.getStockNuevo(), movimiento.getReferencia(), movimiento.getUsuarioId(),
                movimiento.getUsuario(), movimiento.getFecha(), movimiento.getCreatedAt(), movimiento.getUpdateAt());
    }
}
