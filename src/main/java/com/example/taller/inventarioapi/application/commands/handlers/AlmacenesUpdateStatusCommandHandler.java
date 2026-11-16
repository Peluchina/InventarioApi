package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateStatusCommand;
import com.example.taller.inventarioapi.application.validators.AlmacenesUpdateStatusCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesUpdateStatusCommandHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesUpdateStatusCommandValidator validator;

    public void execute(AlmacenesUpdateStatusCommand command) {
        validator.validate(command);
        almacenesRepository.actualizarEstado(command.getId(), command.getEstadoId());
    }
}
