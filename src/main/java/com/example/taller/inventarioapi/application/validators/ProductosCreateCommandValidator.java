package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosCreateCommand;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosCreateCommandValidator {
    private final Validator validator;

    public void validate(ProductosCreateCommand command) {
        Set<ConstraintViolation<ProductosCreateCommand>> errores = validator.validate(command);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
