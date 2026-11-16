package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosCreateCommand;
import com.example.taller.inventarioapi.application.validators.UsuariosCreateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Usuario;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosCreateCommandHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosCreateCommandValidator validator;

    public Integer execute(UsuariosCreateCommand command) {
        validator.validate(command);
        Usuario usuario = Usuario.builder()
                .nombre(command.getNombre())
                .apellidos(command.getApellidos())
                .dni(command.getDni())
                .direccion(command.getDireccion())
                .rolId(command.getRolId())
                .email(command.getEmail())
                .contrasenia(command.getContrasenia())
                .build();
        return usuariosRepository.guardar(usuario);
    }
}
