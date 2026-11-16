package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.CategoriasCreateResponse;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;

@Component
public class CategoriasCreateResponseMapping {
    public CategoriasCreateResponse toResponse(Categoria categoria) {
        return new CategoriasCreateResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}
