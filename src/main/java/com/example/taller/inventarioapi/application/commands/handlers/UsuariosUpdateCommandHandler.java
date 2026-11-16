package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosUpdateCommand;
import com.example.taller.inventarioapi.application.validators.UsuariosUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Usuario;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosUpdateCommandHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosUpdateCommandValidator validator;

    public void execute(UsuariosUpdateCommand command) {
        validator.validate(command);
        Usuario usuario = Usuario.builder()
                .id(command.getId())
                .nombre(command.getNombre())
                .apellidos(command.getApellidos())
                .dni(command.getDni())
                .direccion(command.getDireccion())
                .rolId(command.getRolId())
                .email(command.getEmail())
                .contrasenia(command.getContrasenia())
                .build();
        usuariosRepository.actualizar(usuario);
    }
}
