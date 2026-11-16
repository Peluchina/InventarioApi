package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.dtos.UsuariosGetResponse;
import com.example.taller.inventarioapi.application.mapping.UsuariosGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.UsuariosGetQuery;
import com.example.taller.inventarioapi.domain.aggregates.Usuario;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosGetQueryHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosGetResponseMapping responseMapping;

    public UsuariosGetResponse execute(UsuariosGetQuery query) {
        Usuario usuario = usuariosRepository.buscarPorId(query.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        return responseMapping.toResponse(usuario);
    }
}
