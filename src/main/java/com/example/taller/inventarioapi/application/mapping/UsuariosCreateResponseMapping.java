package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.UsuariosCreateResponse;

@Component
public class UsuariosCreateResponseMapping {
    public UsuariosCreateResponse toResponse(Integer id) {
        return new UsuariosCreateResponse(id);
    }
}
