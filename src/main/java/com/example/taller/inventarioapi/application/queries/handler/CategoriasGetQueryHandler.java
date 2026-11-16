package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.dtos.CategoriasGetResponse;
import com.example.taller.inventarioapi.application.mapping.CategoriasGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.CategoriasGetQuery;
import com.example.taller.inventarioapi.application.validators.CategoriasGetQueryValidator;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasGetQueryHandler {
    private final ICategoriasRepositoryWR categoriasRepository;
    private final CategoriasGetResponseMapping responseMapping;
    private final CategoriasGetQueryValidator validator;

    public CategoriasGetResponse execute(CategoriasGetQuery query) {
        validator.validate(query);
        Categoria categoria = categoriasRepository.buscarPorId(query.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        return responseMapping.toResponse(categoria);
    }
}
