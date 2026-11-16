package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProductosGetResponse;
import com.example.taller.inventarioapi.domain.aggregates.Producto;

@Component
public class ProductosGetResponseMapping {
    public ProductosGetResponse toResponse(Producto producto) {
        return new ProductosGetResponse(
                producto.getId(), producto.getSku(), producto.getNombre(), producto.getDescripcion(),
                producto.getCategoriaId(), producto.getCategoria(), producto.getPrecioCompra(),
                producto.getPrecioVenta(), producto.getStockMinimo(), producto.getStockActual());
    }
}
