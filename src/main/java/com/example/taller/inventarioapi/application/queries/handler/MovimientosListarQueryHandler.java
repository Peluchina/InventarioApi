package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.MovimientosListResponse;
import com.example.taller.inventarioapi.application.mapping.MovimientosListResponseMapping;
import com.example.taller.inventarioapi.application.queries.MovimientosListarQuery;
import com.example.taller.inventarioapi.domain.contracts.IMovimientosInventarioRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientosListarQueryHandler {
    private final IMovimientosInventarioRepositoryWR movimientosRepository;
    private final MovimientosListResponseMapping responseMapping;

    public List<MovimientosListResponse> execute(MovimientosListarQuery query) {
        return movimientosRepository.listar().stream().map(responseMapping::toResponse).toList();
    }
}
