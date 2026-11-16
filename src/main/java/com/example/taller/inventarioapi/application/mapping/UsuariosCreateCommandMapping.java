package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosCreateCommand;
import com.example.taller.inventarioapi.application.dtos.UsuariosCreateRequest;

@Component
public class UsuariosCreateCommandMapping {
    public UsuariosCreateCommand toCommand(UsuariosCreateRequest request) {
        return UsuariosCreateCommand.builder()
                .nombre(request.getNombre())
                .apellidos(request.getApellidos())
                .dni(request.getDni())
                .direccion(request.getDireccion())
                .rolId(request.getRolId())
                .email(request.getEmail())
                .contrasenia(request.getContrasenia())
                .build();
    }
}
