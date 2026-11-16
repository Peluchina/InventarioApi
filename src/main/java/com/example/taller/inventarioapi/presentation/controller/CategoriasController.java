package com.example.taller.inventarioapi.presentation.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.commands.CategoriasCreateCommand;
import com.example.taller.inventarioapi.application.commands.CategoriasDeleteCommand;
import com.example.taller.inventarioapi.application.commands.CategoriasUpdateCommand;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.CategoriasUpdateCommandHandler;
import com.example.taller.inventarioapi.application.dtos.CategoriasCreateRequest;
import com.example.taller.inventarioapi.application.dtos.CategoriasCreateResponse;
import com.example.taller.inventarioapi.application.dtos.CategoriasGetResponse;
import com.example.taller.inventarioapi.application.dtos.CategoriasListResponse;
import com.example.taller.inventarioapi.application.dtos.CategoriasUpdateRequest;
import com.example.taller.inventarioapi.application.dtos.CategoriasUpdateResponse;
import com.example.taller.inventarioapi.application.mapping.CategoriasCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.CategoriasUpdateResponseMapping;
import com.example.taller.inventarioapi.application.parameters.CategoriasDeleteParam;
import com.example.taller.inventarioapi.application.parameters.CategoriasGetParam;
import com.example.taller.inventarioapi.application.parameters.CategoriasUpdateParam;
import com.example.taller.inventarioapi.application.queries.CategoriasGetQuery;
import com.example.taller.inventarioapi.application.queries.CategoriasListQuery;
import com.example.taller.inventarioapi.application.queries.handler.CategoriasGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.CategoriasListQueryHandler;
import com.example.taller.inventarioapi.domain.aggregates.Categoria;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class CategoriasController {
    private final CategoriasCreateCommandMapping createCommandMapping;
    private final CategoriasCreateCommandHandler createCommandHandler;
    private final CategoriasCreateResponseMapping createResponseMapping;
    private final CategoriasListQueryHandler listQueryHandler;
    private final CategoriasGetQueryHandler getQueryHandler;
    private final CategoriasUpdateCommandMapping updateCommandMapping;
    private final CategoriasUpdateCommandHandler updateCommandHandler;
    private final CategoriasUpdateResponseMapping updateResponseMapping;
    private final CategoriasDeleteCommandMapping deleteCommandMapping;
    private final CategoriasDeleteCommandHandler deleteCommandHandler;

    @PostMapping
    public ResponseEntity<CategoriasCreateResponse> crear(@Valid @RequestBody CategoriasCreateRequest request) {
        CategoriasCreateCommand command = createCommandMapping.toCommand(request);
        Categoria categoria = createCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseMapping.toResponse(categoria));
    }

    @GetMapping
    public ResponseEntity<List<CategoriasListResponse>> listar() {
        List<CategoriasListResponse> response = listQueryHandler.execute(new CategoriasListQuery());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriasGetResponse> obtenerPorId(@PathVariable Integer id) {
        CategoriasGetParam param = new CategoriasGetParam(id);
        CategoriasGetResponse response = getQueryHandler.execute(new CategoriasGetQuery(param.getId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriasUpdateResponse> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriasUpdateRequest request) {
        CategoriasUpdateParam param = CategoriasUpdateParam.builder().id(id).build();
        CategoriasUpdateCommand command = updateCommandMapping.toCommand(param, request);
        Categoria categoria = updateCommandHandler.execute(command);
        return ResponseEntity.ok(updateResponseMapping.toResponse(categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        CategoriasDeleteParam param = CategoriasDeleteParam.builder().id(id).build();
        CategoriasDeleteCommand command = deleteCommandMapping.toCommand(param);
        try {
            deleteCommandHandler.execute(command);
        } catch (DataIntegrityViolationException exception) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "No se puede eliminar la categoría porque tiene productos asociados",
                    exception);
        }
        return ResponseEntity.noContent().build();
    }
}
