package com.example.taller.inventarioapi.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.taller.inventarioapi.application.commands.CategoriasCreateCommand;
import com.example.taller.inventarioapi.application.commands.CategoriasDeleteCommand;
import com.example.taller.inventarioapi.application.commands.CategoriasUpdateCommand;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasUpdateCommandHandler;
import com.example.taller.inventarioapi.application.queries.CategoriasGetQuery;
import com.example.taller.inventarioapi.application.validators.CategoriasCreateCommandValidator;
import com.example.taller.inventarioapi.application.validators.CategoriasDeleteCommandValidator;
import com.example.taller.inventarioapi.application.validators.CategoriasGetQueryValidator;
import com.example.taller.inventarioapi.application.validators.CategoriasUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;
import com.example.taller.inventarioapi.domain.contracts.ICategoriasRepositoryWR;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class CategoriasApplicationTest {
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
        CategoriasCreateCommandValidator validator = new CategoriasCreateCommandValidator(beanValidator);
        CategoriasCreateCommand command = CategoriasCreateCommand.builder()
                .nombre(" ")
                .descripcion("Mantenimiento")
                .build();

        assertThrows(ConstraintViolationException.class, () -> validator.validate(command));
    }

    @Test
    void crearPermiteDescripcionOpcional() {
        CategoriasCreateCommandValidator validator = new CategoriasCreateCommandValidator(beanValidator);

        validator.validate(CategoriasCreateCommand.builder().nombre("Herramientas").build());
    }

    @Test
    void crearRechazaTextoQueSuperaQuinientosCaracteres() {
        CategoriasCreateCommandValidator validator = new CategoriasCreateCommandValidator(beanValidator);
        CategoriasCreateCommand command = CategoriasCreateCommand.builder()
                .nombre("Herramientas")
                .descripcion("x".repeat(501))
                .build();

        assertThrows(ConstraintViolationException.class, () -> validator.validate(command));
    }

    @Test
    void consultarRechazaIdNoPositivo() {
        CategoriasGetQueryValidator validator = new CategoriasGetQueryValidator(beanValidator);

        assertThrows(ConstraintViolationException.class, () -> validator.validate(new CategoriasGetQuery(0)));
    }

    @Test
    void crearDevuelveLaCategoriaPersistida() {
        ICategoriasRepositoryWR repository = mock(ICategoriasRepositoryWR.class);
        CategoriasCreateCommandHandler handler = new CategoriasCreateCommandHandler(
                repository, new CategoriasCreateCommandValidator(beanValidator));
        Categoria persistida = Categoria.builder()
                .id(7)
                .nombre("Herramientas")
                .descripcion("Mantenimiento")
                .build();
        when(repository.guardar(any(Categoria.class))).thenReturn(7);
        when(repository.buscarPorId(7)).thenReturn(Optional.of(persistida));

        Categoria resultado = handler.execute(CategoriasCreateCommand.builder()
                .nombre("Herramientas")
                .descripcion("Mantenimiento")
                .build());

        assertEquals(persistida, resultado);
        verify(repository).buscarPorId(7);
    }

    @Test
    void actualizarCategoriaInexistenteDevuelve404() {
        ICategoriasRepositoryWR repository = mock(ICategoriasRepositoryWR.class);
        when(repository.buscarPorId(10)).thenReturn(Optional.empty());
        CategoriasUpdateCommandHandler handler = new CategoriasUpdateCommandHandler(
                repository, new CategoriasUpdateCommandValidator(beanValidator));

        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> handler.execute(CategoriasUpdateCommand.builder()
                        .id(10).nombre("Herramientas").build()));

        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
        verify(repository, never()).actualizar(any(Categoria.class));
    }

    @Test
    void eliminarCategoriaInexistenteDevuelve404() {
        ICategoriasRepositoryWR repository = mock(ICategoriasRepositoryWR.class);
        when(repository.buscarPorId(10)).thenReturn(Optional.empty());
        CategoriasDeleteCommandHandler handler = new CategoriasDeleteCommandHandler(
                repository, new CategoriasDeleteCommandValidator(beanValidator));

        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> handler.execute(new CategoriasDeleteCommand(10)));

        assertEquals(HttpStatus.NOT_FOUND, error.getStatusCode());
        verify(repository, never()).eliminar(10);
    }
}
