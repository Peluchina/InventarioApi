package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresDeleteCommand;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresDeleteCommandValidator {
    private final Validator validator;

    public void validate(ProveedoresDeleteCommand command) {

        Set<ConstraintViolation<ProveedoresDeleteCommand>> errores =
                validator.validate(command);

        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
