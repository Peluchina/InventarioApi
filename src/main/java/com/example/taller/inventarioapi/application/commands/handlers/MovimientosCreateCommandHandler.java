package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.MovimientosCreateCommand;
import com.example.taller.inventarioapi.application.mapping.MovimientosCreateResponseMapping;
import com.example.taller.inventarioapi.application.dtos.MovimientosCreateResponse;
import com.example.taller.inventarioapi.application.validators.MovimientosCreateValidator;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;
import com.example.taller.inventarioapi.domain.contracts.IMovimientosInventarioRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientosCreateCommandHandler {
    private final IMovimientosInventarioRepositoryWR movimientosRepository;
    private final MovimientosCreateValidator validator;
    private final MovimientosCreateResponseMapping responseMapping;

    public MovimientosCreateResponse execute(MovimientosCreateCommand command) {
        validator.validate(command);
        MovimientoInventario movimiento = MovimientoInventario.builder()
                .productoId(command.getProductoId())
                .tipoMovimiento(command.getTipoMovimiento())
                .cantidad(command.getCantidad())
                .referencia(command.getReferencia())
                .usuarioId(command.getUsuarioId())
                .build();
        return responseMapping.toResponse(movimientosRepository.registrar(movimiento));
    }
}
