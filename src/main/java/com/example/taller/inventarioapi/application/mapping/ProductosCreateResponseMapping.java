package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProductosCreateResponse;

@Component
public class ProductosCreateResponseMapping {
    public ProductosCreateResponse toResponse(Integer id) {
        return ProductosCreateResponse.builder()
                .id(id)
                .mensaje("Producto creado correctamente")
                .build();
    }
}
