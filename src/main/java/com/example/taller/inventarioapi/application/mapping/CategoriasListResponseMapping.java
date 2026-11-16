package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.CategoriasListResponse;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;

@Component
public class CategoriasListResponseMapping {
    public CategoriasListResponse toResponse(Categoria categoria) {
        return new CategoriasListResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}
