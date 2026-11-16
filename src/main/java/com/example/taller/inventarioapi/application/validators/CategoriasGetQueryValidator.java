package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.queries.CategoriasGetQuery;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CategoriasGetQueryValidator {
    private final Validator validator;

    public void validate(CategoriasGetQuery query) {
        Set<ConstraintViolation<CategoriasGetQuery>> errores = validator.validate(query);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
    }
}
