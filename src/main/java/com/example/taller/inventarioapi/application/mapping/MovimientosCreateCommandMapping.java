package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.MovimientosCreateCommand;
import com.example.taller.inventarioapi.application.dtos.MovimientosCreateRequest;

@Component
public class MovimientosCreateCommandMapping {
    public MovimientosCreateCommand toCommand(MovimientosCreateRequest request) {
        return MovimientosCreateCommand.builder()
                .productoId(request.getProductoId())
                .tipoMovimiento(request.getTipoMovimiento())
                .cantidad(request.getCantidad())
                .referencia(request.getReferencia())
                .usuarioId(request.getUsuarioId())
                .build();
    }
}
