package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.UsuariosListResponse;
import com.example.taller.inventarioapi.application.mapping.UsuariosListResponseMapping;
import com.example.taller.inventarioapi.application.queries.UsuariosListQuery;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosListQueryHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosListResponseMapping responseMapping;

    public List<UsuariosListResponse> execute(UsuariosListQuery query) {
        return usuariosRepository.listar().stream().map(responseMapping::toResponse).toList();
    }
}
