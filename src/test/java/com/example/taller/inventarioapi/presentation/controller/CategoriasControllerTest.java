package com.example.taller.inventarioapi.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.CategoriasDeleteCommand;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasUpdateCommandHandler;
import com.example.taller.inventarioapi.application.mapping.CategoriasCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasUpdateResponseMapping;
import com.example.taller.inventarioapi.application.parameters.CategoriasDeleteParam;
import com.example.taller.inventarioapi.application.queries.handler.CategoriasGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.CategoriasListQueryHandler;

class CategoriasControllerTest {
    @Test
    void eliminarCategoriaUsadaDevuelve409() {
        CategoriasDeleteCommandMapping deleteMapping = mock(CategoriasDeleteCommandMapping.class);
        CategoriasDeleteCommandHandler deleteHandler = mock(CategoriasDeleteCommandHandler.class);
        when(deleteMapping.toCommand(any(CategoriasDeleteParam.class)))
                .thenReturn(new CategoriasDeleteCommand(5));
        doThrow(new DataIntegrityViolationException("fk_producto_categoria"))
                .when(deleteHandler).execute(any(CategoriasDeleteCommand.class));
        CategoriasController controller = new CategoriasController(
                mock(CategoriasCreateCommandMapping.class),
                mock(CategoriasCreateCommandHandler.class),
                mock(CategoriasCreateResponseMapping.class),
                mock(CategoriasListQueryHandler.class),
                mock(CategoriasGetQueryHandler.class),
                mock(CategoriasUpdateCommandMapping.class),
                mock(CategoriasUpdateCommandHandler.class),
                mock(CategoriasUpdateResponseMapping.class),
                deleteMapping,
                deleteHandler);

        ResponseStatusException error = assertThrows(ResponseStatusException.class,
                () -> controller.eliminar(5));

        assertEquals(HttpStatus.CONFLICT, error.getStatusCode());
    }
}
