package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateStatusCommand;
import com.example.taller.inventarioapi.application.validators.ProveedoresUpdateStatusCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresUpdateStatusCommandHandler {
    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresUpdateStatusCommandValidator validator;

    public void execute(ProveedoresUpdateStatusCommand command) {

        validator.validate(command);

        proveedoresRepository.actualizarEstado(
                command.getId(),
                command.getEstadoId()
        );
    }
}
