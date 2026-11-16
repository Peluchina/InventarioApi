package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosUpdateCommand;
import com.example.taller.inventarioapi.application.dtos.ProductosUpdateRequest;
import com.example.taller.inventarioapi.application.parameters.ProductosUpdateParam;

@Component
public class ProductosUpdateCommandMapping {
    public ProductosUpdateCommand toCommand(ProductosUpdateParam param, ProductosUpdateRequest request) {
        return ProductosUpdateCommand.builder()
                .id(param.getId())
                .sku(request.getSku())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .categoriaId(request.getCategoriaId())
                .precioCompra(request.getPrecioCompra())
                .precioVenta(request.getPrecioVenta())
                .stockMinimo(request.getStockMinimo())
                .build();
    }
}
