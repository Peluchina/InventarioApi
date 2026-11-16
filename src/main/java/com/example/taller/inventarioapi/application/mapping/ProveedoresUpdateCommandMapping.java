package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateCommand;
import com.example.taller.inventarioapi.application.dtos.ProveedoresUpdateRequest;
import com.example.taller.inventarioapi.application.parameters.ProveedoresUpdateParam;

@Component 
public class ProveedoresUpdateCommandMapping {
    public ProveedoresUpdateCommand toCommand(
            ProveedoresUpdateParam param,
            ProveedoresUpdateRequest request) {

        return ProveedoresUpdateCommand.builder()
                .id(param.getId())
                .nombre(request.getNombre())
                .contacto(request.getContacto())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .build();
    }
}
