package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateStatusCommand;
import com.example.taller.inventarioapi.application.dtos.AlmacenesUpdateStatusRequest;
import com.example.taller.inventarioapi.application.parameters.AlmacenesUpdateStatusParam;

@Component
public class AlmacenesUpdateStatusCommandMapping {
    public AlmacenesUpdateStatusCommand toCommand(
            AlmacenesUpdateStatusParam param, AlmacenesUpdateStatusRequest request) {
        return AlmacenesUpdateStatusCommand.builder()
                .id(param.getId())
                .estadoId(request.getEstadoId())
                .build();
    }
}
