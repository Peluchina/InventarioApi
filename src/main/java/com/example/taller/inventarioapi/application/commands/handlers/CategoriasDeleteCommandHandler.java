package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.CategoriasDeleteCommand;
import com.example.taller.inventarioapi.application.validators.CategoriasDeleteCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasDeleteCommandHandler {
    private final ICategoriasRepositoryWR categoriasRepository;
    private final CategoriasDeleteCommandValidator validator;

    public void execute(CategoriasDeleteCommand command) {
        validator.validate(command);
        if (categoriasRepository.buscarPorId(command.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }
        categoriasRepository.eliminar(command.getId());
    }
}
