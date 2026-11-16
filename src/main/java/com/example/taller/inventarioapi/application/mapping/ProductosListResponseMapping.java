package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProductosListResponse;
import com.example.taller.inventarioapi.domain.aggregates.Producto;

@Component
public class ProductosListResponseMapping {
    public ProductosListResponse toResponse(Producto producto) {
        return new ProductosListResponse(
                producto.getId(), producto.getSku(), producto.getNombre(), producto.getDescripcion(),
                producto.getCategoriaId(), producto.getCategoria(), producto.getPrecioCompra(),
                producto.getPrecioVenta(), producto.getStockMinimo(), producto.getStockActual(),
                producto.getEstadoId(), producto.getEstadoStock());
    }
}
