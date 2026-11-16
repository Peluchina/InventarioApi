package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.UsuariosGetResponse;
import com.example.taller.inventarioapi.domain.aggregates.Usuario;

@Component
public class UsuariosGetResponseMapping {
    public UsuariosGetResponse toResponse(Usuario usuario) {
        return new UsuariosGetResponse(
                usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
                usuario.getDni(), usuario.getDireccion(), usuario.getRolId(),
                usuario.getEmail(), usuario.getEstadoId());
    }
}
