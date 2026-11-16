package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Controller;

import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateStatusCommand;
import com.example.taller.inventarioapi.application.dtos.ProveedoresUpdateStatusRequest;
import com.example.taller.inventarioapi.application.parameters.ProveedoresUpdateStatusParam;

@Controller 
public class ProveedoresUpdateStatusCommandMapping {
    public ProveedoresUpdateStatusCommand toCommand(
            ProveedoresUpdateStatusParam param,
            ProveedoresUpdateStatusRequest request) {

        return ProveedoresUpdateStatusCommand.builder()
                .id(param.getId())
                .estadoId(request.getEstadoId())
                .build();
    }
}
