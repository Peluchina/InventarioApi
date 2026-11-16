package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.UsuariosListResponse;
import com.example.taller.inventarioapi.domain.aggregates.Usuario;

@Component
public class UsuariosListResponseMapping {
    public UsuariosListResponse toResponse(Usuario usuario) {
        return new UsuariosListResponse(
                usuario.getId(), usuario.getNombre(), usuario.getApellidos(),
                usuario.getDni(), usuario.getDireccion(), usuario.getRolId(),
                usuario.getEmail(), usuario.getEstadoId());
    }
}
