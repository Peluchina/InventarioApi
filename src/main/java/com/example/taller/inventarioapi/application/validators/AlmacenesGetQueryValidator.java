package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.queries.AlmacenesGetQuery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesGetQueryValidator {
    private final Validator validator;

    public void validate(AlmacenesGetQuery query) {
        Set<ConstraintViolation<AlmacenesGetQuery>> errores = validator.validate(query);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
