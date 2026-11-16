package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosCreateCommand;
import com.example.taller.inventarioapi.application.dtos.ProductosCreateRequest;

@Component
public class ProductosCreateCommandMapping {
    public ProductosCreateCommand toCommand(ProductosCreateRequest request) {
        return ProductosCreateCommand.builder()
                .sku(request.getSku())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .categoriaId(request.getCategoriaId())
                .precioCompra(request.getPrecioCompra())
                .precioVenta(request.getPrecioVenta())
                .stockMinimo(request.getStockMinimo())
                .stockActual(request.getStockActual())
                .build();
    }
}
