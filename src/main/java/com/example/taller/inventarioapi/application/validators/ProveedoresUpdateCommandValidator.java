package com.example.taller.inventarioapi.application.validators;

import java.util.Set;
import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateCommand;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresUpdateCommandValidator {
    private final jakarta.validation.Validator validator;

    public void validate(ProveedoresUpdateCommand command) {

        Set<ConstraintViolation<ProveedoresUpdateCommand>> errores =
                validator.validate(command);

        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
