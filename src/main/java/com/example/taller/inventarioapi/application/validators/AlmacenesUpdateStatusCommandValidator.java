package com.example.taller.inventarioapi.application.validators;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateStatusCommand;
import com.example.taller.inventarioapi.domain.contracts.IEstadosRespositoryWR;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesUpdateStatusCommandValidator {
    private final Validator validator;
    private final IEstadosRespositoryWR estadosRepository;

    public void validate(AlmacenesUpdateStatusCommand command) {
        Set<ConstraintViolation<AlmacenesUpdateStatusCommand>> errores = validator.validate(command);
        if (!errores.isEmpty()) {
            throw new ConstraintViolationException(errores);
        }
        if (estadosRepository.buscarPorId(command.getEstadoId()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El estado indicado no existe");
        }
    }
}
