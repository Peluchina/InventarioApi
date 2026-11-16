package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateCommand;
import com.example.taller.inventarioapi.application.dtos.AlmacenesUpdateRequest;
import com.example.taller.inventarioapi.application.parameters.AlmacenesUpdateParam;

@Component
public class AlmacenesUpdateCommandMapping {
    public AlmacenesUpdateCommand toCommand(AlmacenesUpdateParam param, AlmacenesUpdateRequest request) {
        return AlmacenesUpdateCommand.builder()
                .id(param.getId())
                .nombre(request.getNombre())
                .ubicacion(request.getUbicacion())
                .build();
    }
}
