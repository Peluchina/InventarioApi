package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateCommand;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesUpdateCommandValidator {
    private final Validator validator;

    public void validate(AlmacenesUpdateCommand command) {
        Set<ConstraintViolation<AlmacenesUpdateCommand>> errores = validator.validate(command);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
