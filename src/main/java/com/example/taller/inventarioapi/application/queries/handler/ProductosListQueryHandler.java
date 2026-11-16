package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProductosListResponse;
import com.example.taller.inventarioapi.application.mapping.ProductosListResponseMapping;
import com.example.taller.inventarioapi.application.queries.ProductosListQuery;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosListQueryHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosListResponseMapping responseMapping;

    public List<ProductosListResponse> execute(ProductosListQuery query) {
        return productosRepository.listar().stream().map(responseMapping::toResponse).toList();
    }
}
