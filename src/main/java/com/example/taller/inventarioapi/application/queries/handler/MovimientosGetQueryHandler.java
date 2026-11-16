package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.dtos.MovimientosGetResponse;
import com.example.taller.inventarioapi.application.mapping.MovimientosGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.MovimientosGetQuery;
import com.example.taller.inventarioapi.domain.aggregates.MovimientoInventario;
import com.example.taller.inventarioapi.domain.contracts.IMovimientosInventarioRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientosGetQueryHandler {
    private final IMovimientosInventarioRepositoryWR movimientosRepository;
    private final MovimientosGetResponseMapping responseMapping;

    public MovimientosGetResponse execute(MovimientosGetQuery query) {
        MovimientoInventario movimiento = movimientosRepository.buscarPorId(query.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movimiento no encontrado"));
        return responseMapping.toResponse(movimiento);
    }
}
