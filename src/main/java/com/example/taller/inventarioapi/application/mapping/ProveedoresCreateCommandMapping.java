package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresCreateCommand;
import com.example.taller.inventarioapi.application.dtos.ProveedoresCreateRequest;


@Component 
public class ProveedoresCreateCommandMapping {

    public ProveedoresCreateCommand toCommand(ProveedoresCreateRequest request) {

        return ProveedoresCreateCommand.builder()
                .nombre(request.getNombre())
                .contacto(request.getContacto())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .direccion(request.getDireccion())
                .build();
    }
}
