package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresDeleteCommand;
import com.example.taller.inventarioapi.application.validators.ProveedoresDeleteCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresDeleteCommandHandler {

    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresDeleteCommandValidator validator;

    public void execute(ProveedoresDeleteCommand command) {

        validator.validate(command);

        proveedoresRepository.eliminar(command.getId());
    }
}
