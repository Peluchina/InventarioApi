package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.validators.ProductosUpdateStatusCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosUpdateStatusCommandHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosUpdateStatusCommandValidator validator;

    public void execute(ProductosUpdateStatusCommand command) {
        validator.validate(command);
        productosRepository.actualizarEstado(command.getId(), command.getEstadoId());
    }
}
