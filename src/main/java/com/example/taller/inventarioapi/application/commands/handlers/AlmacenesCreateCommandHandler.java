package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.AlmacenesCreateCommand;
import com.example.taller.inventarioapi.application.validators.AlmacenesCreateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesCreateCommandHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesCreateCommandValidator validator;

    public Almacen execute(AlmacenesCreateCommand command) {
        validator.validate(command);
        Almacen almacen = Almacen.builder()
                .nombre(command.getNombre())
                .ubicacion(command.getUbicacion())
                .build();
        Integer id = almacenesRepository.guardar(almacen);
        return almacenesRepository.buscarPorId(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR, "No se pudo recuperar el almacén creado"));
    }
}
