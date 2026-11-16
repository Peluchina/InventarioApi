package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesDeleteCommand;
import com.example.taller.inventarioapi.application.validators.AlmacenesDeleteCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesDeleteCommandHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesDeleteCommandValidator validator;

    public void execute(AlmacenesDeleteCommand command) {
        validator.validate(command);
        almacenesRepository.eliminar(command.getId());
    }
}
