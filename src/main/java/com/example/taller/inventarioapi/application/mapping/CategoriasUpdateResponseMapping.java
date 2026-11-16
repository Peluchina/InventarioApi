package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.CategoriasUpdateResponse;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;

@Component
public class CategoriasUpdateResponseMapping {
    public CategoriasUpdateResponse toResponse(Categoria categoria) {
        return new CategoriasUpdateResponse(categoria.getId(), categoria.getNombre(), categoria.getDescripcion());
    }
}
