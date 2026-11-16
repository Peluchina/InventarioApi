package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.MovimientosPorProductoResponse;
import com.example.taller.inventarioapi.application.mapping.MovimientosPorProductoResponseMapping;
import com.example.taller.inventarioapi.application.queries.MovimientosPorProductoQuery;
import com.example.taller.inventarioapi.domain.contracts.IMovimientosInventarioRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MovimientosPorProductoQueryHandler {
    private final IMovimientosInventarioRepositoryWR movimientosRepository;
    private final MovimientosPorProductoResponseMapping responseMapping;

    public List<MovimientosPorProductoResponse> execute(MovimientosPorProductoQuery query) {
        return movimientosRepository.listarPorProducto(query.getProductoId())
                .stream().map(responseMapping::toResponse).toList();
    }
}
