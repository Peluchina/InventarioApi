package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.MovimientosPorProductoResponse;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;

@Component
public class MovimientosPorProductoResponseMapping {
    public MovimientosPorProductoResponse toResponse(MovimientoInventario movimiento) {
        return new MovimientosPorProductoResponse(
                movimiento.getId(), movimiento.getProductoId(), movimiento.getProducto(),
                movimiento.getTipoMovimiento(), movimiento.getCantidad(), movimiento.getStockAnterior(),
                movimiento.getStockNuevo(), movimiento.getReferencia(), movimiento.getUsuario(),
                movimiento.getFecha(), movimiento.getCreatedAt());
    }
}
