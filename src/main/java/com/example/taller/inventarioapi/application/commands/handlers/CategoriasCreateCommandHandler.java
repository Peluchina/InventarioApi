package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.CategoriasCreateCommand;
import com.example.taller.inventarioapi.application.validators.CategoriasCreateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasCreateCommandHandler {
    private final ICategoriasRepositoryWR categoriasRepository;
    private final CategoriasCreateCommandValidator validator;

    public Categoria execute(CategoriasCreateCommand command) {
        validator.validate(command);
        Categoria categoria = Categoria.builder()
                .nombre(command.getNombre())
                .descripcion(command.getDescripcion())
                .build();
        Integer id = categoriasRepository.guardar(categoria);
        return categoriasRepository.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar la categoría creada"));
    }
}
