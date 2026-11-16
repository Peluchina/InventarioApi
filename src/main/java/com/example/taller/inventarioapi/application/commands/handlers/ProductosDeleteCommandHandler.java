package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosDeleteCommand;
import com.example.taller.inventarioapi.application.validators.ProductosDeleteCommandValidator;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosDeleteCommandHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosDeleteCommandValidator validator;

    public void execute(ProductosDeleteCommand command) {
        validator.validate(command);
        productosRepository.eliminar(command.getId());
    }
}
