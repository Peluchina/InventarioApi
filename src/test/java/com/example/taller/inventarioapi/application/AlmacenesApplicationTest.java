package com.example.taller.inventarioapi.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.AlmacenesCreateCommand;
import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateStatusCommand;
import com.example.taller.inventarioapi.application.commands.handlers.AlmacenesCreateCommandHandler;
import com.example.taller.inventarioapi.application.queries.AlmacenesGetQuery;
import com.example.taller.inventarioapi.application.validators.AlmacenesCreateCommandValidator;
import com.example.taller.inventarioapi.application.validators.AlmacenesGetQueryValidator;
import com.example.taller.inventarioapi.application.validators.AlmacenesUpdateStatusCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;
import com.example.taller.inventarioapi.domain.contracts.IEstadosRespositoryWR;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AlmacenesApplicationTest {
    private static ValidatorFactory validatorFactory;
    private static Validator beanValidator;

    @BeforeAll
    static void configurarValidador() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        beanValidator = validatorFactory.getValidator();
    }

    @AfterAll
    static void cerrarValidador() {
        validatorFactory.close();
    }

    @Test
    void crearRechazaNombreVacio() {
        AlmacenesCreateCommandValidator validator = new AlmacenesCreateCommandValidator(beanValidator);
        AlmacenesCreateCommand command = AlmacenesCreateCommand.builder()
                .nombre(" ")
                .ubicacion("Lima")
                .build();

        assertThrows(ConstraintViolationException.class, () -> validator.validate(command));
    }

    @Test
    void crearRechazaCamposQueSuperanQuinientosCaracteres() {
        AlmacenesCreateCommandValidator validator = new AlmacenesCreateCommandValidator(beanValidator);
        String textoLargo = "x".repeat(501);
        AlmacenesCreateCommand command = AlmacenesCreateCommand.builder()
                .nombre(textoLargo)
                .ubicacion("Lima")
                .build();

        assertThrows(ConstraintViolationException.class, () -> validator.validate(command));
    }

    @Test
    void consultarRechazaIdsNoPositivos() {
        AlmacenesGetQueryValidator validator = new AlmacenesGetQueryValidator(beanValidator);

        assertThrows(ConstraintViolationException.class, () -> validator.validate(new AlmacenesGetQuery(0)));
    }

    @Test
    void cambiarEstadoRechazaEstadoInexistente() {
        IEstadosRespositoryWR estadosRepository = mock(IEstadosRespositoryWR.class);
        when(estadosRepository.buscarPorId(9)).thenReturn(Optional.empty());
        AlmacenesUpdateStatusCommandValidator validator =
                new AlmacenesUpdateStatusCommandValidator(beanValidator, estadosRepository);

        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> validator.validate(new AlmacenesUpdateStatusCommand(4, 9)));

        assertEquals(HttpStatus.BAD_REQUEST, error.getStatusCode());
    }

    @Test
    void cambiarEstadoRechazaIdInvalidoAntesDeConsultarEstado() {
        IEstadosRespositoryWR estadosRepository = mock(IEstadosRespositoryWR.class);
        AlmacenesUpdateStatusCommandValidator validator =
                new AlmacenesUpdateStatusCommandValidator(beanValidator, estadosRepository);

        assertThrows(ConstraintViolationException.class,
                () -> validator.validate(new AlmacenesUpdateStatusCommand(0, 1)));
        verify(estadosRepository, never()).buscarPorId(1);
    }

    @Test
    void crearDevuelveDatosYEstadoPersistidos() {
        IAlmacenesRepositoryWR almacenesRepository = mock(IAlmacenesRepositoryWR.class);
        AlmacenesCreateCommandValidator validator = new AlmacenesCreateCommandValidator(beanValidator);
        AlmacenesCreateCommandHandler handler = new AlmacenesCreateCommandHandler(almacenesRepository, validator);
        Almacen persistido = Almacen.builder()
                .id(17)
                .nombre("Almacén Principal")
                .ubicacion("Lima")
                .estadoId(3)
                .build();
        when(almacenesRepository.guardar(org.mockito.ArgumentMatchers.any(Almacen.class))).thenReturn(17);
        when(almacenesRepository.buscarPorId(17)).thenReturn(Optional.of(persistido));

        Almacen resultado = handler.execute(AlmacenesCreateCommand.builder()
                .nombre("Almacén Principal")
                .ubicacion("Lima")
                .build());

        assertEquals(persistido, resultado);
        verify(almacenesRepository).buscarPorId(17);
    }
}
