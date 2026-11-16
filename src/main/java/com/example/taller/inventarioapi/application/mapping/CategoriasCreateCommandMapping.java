package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.CategoriasCreateCommand;
import com.example.taller.inventarioapi.application.dtos.CategoriasCreateRequest;

@Component
public class CategoriasCreateCommandMapping {
    public CategoriasCreateCommand toCommand(CategoriasCreateRequest request) {
        return CategoriasCreateCommand.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
    }
}
