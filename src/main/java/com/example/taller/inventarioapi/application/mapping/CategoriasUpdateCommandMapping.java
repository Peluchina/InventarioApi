package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.CategoriasUpdateCommand;
import com.example.taller.inventarioapi.application.dtos.CategoriasUpdateRequest;
import com.example.taller.inventarioapi.application.parameters.CategoriasUpdateParam;

@Component
public class CategoriasUpdateCommandMapping {
    public CategoriasUpdateCommand toCommand(CategoriasUpdateParam param, CategoriasUpdateRequest request) {
        return CategoriasUpdateCommand.builder()
                .id(param.getId())
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .build();
    }
}
