package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import jakarta.validation.Validator;
import org.springframework.stereotype.Component;
import com.example.taller.inventarioapi.application.commands.ProveedoresCreateCommand;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor  
public class ProveedoresCreateCommandValidator {
    private final Validator validator;

    public void validate(ProveedoresCreateCommand command) {

        Set<ConstraintViolation<ProveedoresCreateCommand>> errores =
                validator.validate(command);

        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
