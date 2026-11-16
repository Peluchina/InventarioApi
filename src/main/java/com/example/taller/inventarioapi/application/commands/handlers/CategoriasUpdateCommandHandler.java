package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.CategoriasUpdateCommand;
import com.example.taller.inventarioapi.application.validators.CategoriasUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasUpdateCommandHandler {
    private final ICategoriasRepositoryWR categoriasRepository;
    private final CategoriasUpdateCommandValidator validator;

    public Categoria execute(CategoriasUpdateCommand command) {
        validator.validate(command);
        if (categoriasRepository.buscarPorId(command.getId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada");
        }

        Categoria categoria = Categoria.builder()
                .id(command.getId())
                .nombre(command.getNombre())
                .descripcion(command.getDescripcion())
                .build();
        categoriasRepository.actualizar(categoria);
        return categoriasRepository.buscarPorId(command.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }
}
