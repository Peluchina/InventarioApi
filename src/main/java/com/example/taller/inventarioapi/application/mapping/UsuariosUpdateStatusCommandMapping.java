package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.dtos.UsuariosUpdateStatusRequest;
import com.example.taller.inventarioapi.application.parameters.UsuariosUpdateStatusParam;

@Component
public class UsuariosUpdateStatusCommandMapping {
    public UsuariosUpdateStatusCommand toCommand(
            UsuariosUpdateStatusParam param, UsuariosUpdateStatusRequest request) {
        return UsuariosUpdateStatusCommand.builder()
                .id(param.getId())
                .estadoId(request.getEstadoId())
                .build();
    }
}
