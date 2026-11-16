package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesCreateCommand;
import com.example.taller.inventarioapi.application.dtos.AlmacenesCreateRequest;

@Component
public class AlmacenesCreateCommandMapping {
    public AlmacenesCreateCommand toCommand(AlmacenesCreateRequest request) {
        return AlmacenesCreateCommand.builder()
                .nombre(request.getNombre())
                .ubicacion(request.getUbicacion())
                .build();
    }
}
