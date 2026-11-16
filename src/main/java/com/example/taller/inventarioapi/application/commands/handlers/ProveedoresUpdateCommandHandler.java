package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateCommand;
import com.example.taller.inventarioapi.application.validators.ProveedoresUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Proveedor;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresUpdateCommandHandler {

    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresUpdateCommandValidator validator;

     public void execute(ProveedoresUpdateCommand command) {

        validator.validate(command);
        Proveedor proveedor = Proveedor.builder()
                .id(command.getId())
                .nombre(command.getNombre())
                .contacto(command.getContacto())
                .telefono(command.getTelefono())
                .email(command.getEmail())
                .direccion(command.getDireccion())
                .build();

        proveedoresRepository.actualizar(proveedor);
    }
}
