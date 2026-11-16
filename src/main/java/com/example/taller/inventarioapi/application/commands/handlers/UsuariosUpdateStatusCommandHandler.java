package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.validators.UsuariosUpdateStatusCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosUpdateStatusCommandHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosUpdateStatusCommandValidator validator;

    public void execute(UsuariosUpdateStatusCommand command) {
        validator.validate(command);
        usuariosRepository.actualizarEstado(command.getId(), command.getEstadoId());
    }
}
