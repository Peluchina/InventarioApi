package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.dtos.ProductosGetResponse;
import com.example.taller.inventarioapi.application.mapping.ProductosGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.ProductosGetQuery;
import com.example.taller.inventarioapi.domain.aggregates.Producto;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosGetQueryHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosGetResponseMapping responseMapping;

    public ProductosGetResponse execute(ProductosGetQuery query) {
        Producto producto = productosRepository.buscarPorId(query.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
        return responseMapping.toResponse(producto);
    }
}
