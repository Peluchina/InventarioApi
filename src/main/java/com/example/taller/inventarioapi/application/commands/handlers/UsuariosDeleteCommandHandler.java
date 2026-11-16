package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosDeleteCommand;
import com.example.taller.inventarioapi.application.validators.UsuariosDeleteCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IUsuariosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UsuariosDeleteCommandHandler {
    private final IUsuariosRepositoryWR usuariosRepository;
    private final UsuariosDeleteCommandValidator validator;

    public void execute(UsuariosDeleteCommand command) {
        validator.validate(command);
        usuariosRepository.eliminar(command.getId());
    }
}
