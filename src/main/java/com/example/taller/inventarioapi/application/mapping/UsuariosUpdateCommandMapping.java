package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosUpdateCommand;
import com.example.taller.inventarioapi.application.dtos.UsuariosUpdateRequest;
import com.example.taller.inventarioapi.application.parameters.UsuariosUpdateParam;

@Component
public class UsuariosUpdateCommandMapping {
    public UsuariosUpdateCommand toCommand(UsuariosUpdateParam param, UsuariosUpdateRequest request) {
        return UsuariosUpdateCommand.builder()
                .id(param.getId())
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
