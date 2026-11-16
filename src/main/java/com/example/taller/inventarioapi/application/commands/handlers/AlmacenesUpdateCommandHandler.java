package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateCommand;
import com.example.taller.inventarioapi.application.validators.AlmacenesUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesUpdateCommandHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesUpdateCommandValidator validator;

    public void execute(AlmacenesUpdateCommand command) {
        validator.validate(command);
        Almacen almacen = Almacen.builder()
                .id(command.getId())
                .nombre(command.getNombre())
                .ubicacion(command.getUbicacion())
                .build();
        almacenesRepository.actualizar(almacen);
    }
}
