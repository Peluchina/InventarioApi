package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.CategoriasGetResponse;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;

@Component
public class CategoriasGetResponseMapping {
    public CategoriasGetResponse toResponse(Categoria categoria) {
        return new CategoriasGetResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}
