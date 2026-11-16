package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosUpdateCommand;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosUpdateCommandValidator {
    private final Validator validator;

    public void validate(ProductosUpdateCommand command) {
        Set<ConstraintViolation<ProductosUpdateCommand>> errores = validator.validate(command);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
