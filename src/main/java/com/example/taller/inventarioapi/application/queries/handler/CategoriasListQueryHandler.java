package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.CategoriasListResponse;
import com.example.taller.inventarioapi.application.mapping.CategoriasListResponseMapping;
import com.example.taller.inventarioapi.application.queries.CategoriasListQuery;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasListQueryHandler {
    private final ICategoriasRepositoryWR categoriasRepository;
    private final CategoriasListResponseMapping responseMapping;

    public List<CategoriasListResponse> execute(CategoriasListQuery query) {
        return categoriasRepository.listar().stream().map(responseMapping::toResponse).toList();
    }
}
