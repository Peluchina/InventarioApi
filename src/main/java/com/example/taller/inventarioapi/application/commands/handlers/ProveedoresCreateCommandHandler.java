package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresCreateCommand;
import com.example.taller.inventarioapi.application.validators.ProveedoresCreateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Proveedor;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresCreateCommandHandler {

    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresCreateCommandValidator validator;

    public Integer execute(ProveedoresCreateCommand command) {

        validator.validate(command);
        Proveedor proveedor = Proveedor.builder()
                .nombre(command.getNombre())
                .contacto(command.getContacto())
                .telefono(command.getTelefono())
                .email(command.getEmail())
                .direccion(command.getDireccion())
                .build();

        return proveedoresRepository.guardar(proveedor);
    }

}
