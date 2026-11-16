package com.example.taller.inventarioapi.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.taller.inventarioapi.application.commands.UsuariosCreateCommand;
import com.example.taller.inventarioapi.application.commands.UsuariosDeleteCommand;
import com.example.taller.inventarioapi.application.commands.UsuariosUpdateCommand;
import com.example.taller.inventarioapi.application.commands.UsuariosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.commands.handlers.UsuariosCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.UsuariosDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.UsuariosUpdateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.UsuariosUpdateStatusCommandHandler;
import com.example.taller.inventarioapi.application.dtos.UsuariosCreateRequest;
import com.example.taller.inventarioapi.application.dtos.UsuariosCreateResponse;
import com.example.taller.inventarioapi.application.dtos.UsuariosGetResponse;
import com.example.taller.inventarioapi.application.dtos.UsuariosListResponse;
import com.example.taller.inventarioapi.application.dtos.UsuariosUpdateRequest;
import com.example.taller.inventarioapi.application.dtos.UsuariosUpdateStatusRequest;
import com.example.taller.inventarioapi.application.mapping.UsuariosCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.UsuariosCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.UsuariosDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.UsuariosUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.UsuariosUpdateStatusCommandMapping;
import com.example.taller.inventarioapi.application.parameters.UsuariosDeleteParam;
import com.example.taller.inventarioapi.application.parameters.UsuariosGetParam;
import com.example.taller.inventarioapi.application.parameters.UsuariosUpdateParam;
import com.example.taller.inventarioapi.application.parameters.UsuariosUpdateStatusParam;
import com.example.taller.inventarioapi.application.queries.UsuariosGetQuery;
import com.example.taller.inventarioapi.application.queries.UsuariosListQuery;
import com.example.taller.inventarioapi.application.queries.handler.UsuariosGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.UsuariosListQueryHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UsuariosController {
    private final UsuariosCreateCommandMapping createCommandMapping;
    private final UsuariosCreateCommandHandler createCommandHandler;
    private final UsuariosCreateResponseMapping createResponseMapping;
    private final UsuariosListQueryHandler listQueryHandler;
    private final UsuariosGetQueryHandler getQueryHandler;
    private final UsuariosUpdateCommandMapping updateCommandMapping;
    private final UsuariosUpdateCommandHandler updateCommandHandler;
    private final UsuariosUpdateStatusCommandMapping updateStatusCommandMapping;
    private final UsuariosUpdateStatusCommandHandler updateStatusCommandHandler;
    private final UsuariosDeleteCommandMapping deleteCommandMapping;
    private final UsuariosDeleteCommandHandler deleteCommandHandler;

    @PostMapping
    public ResponseEntity<UsuariosCreateResponse> crear(@Valid @RequestBody UsuariosCreateRequest request) {
        UsuariosCreateCommand command = createCommandMapping.toCommand(request);
        Integer id = createCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseMapping.toResponse(id));
    }

    @GetMapping
    public ResponseEntity<List<UsuariosListResponse>> listar() {
        List<UsuariosListResponse> response = listQueryHandler.execute(new UsuariosListQuery());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuariosGetResponse> obtenerPorId(@PathVariable Integer id) {
        UsuariosGetParam param = new UsuariosGetParam(id);
        UsuariosGetResponse response = getQueryHandler.execute(new UsuariosGetQuery(param.getId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody UsuariosUpdateRequest request) {
        UsuariosUpdateParam param = UsuariosUpdateParam.builder().id(id).build();
        UsuariosUpdateCommand command = updateCommandMapping.toCommand(param, request);
        updateCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody UsuariosUpdateStatusRequest request) {
        UsuariosUpdateStatusParam param = UsuariosUpdateStatusParam.builder().id(id).build();
        UsuariosUpdateStatusCommand command = updateStatusCommandMapping.toCommand(param, request);
        updateStatusCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        UsuariosDeleteParam param = UsuariosDeleteParam.builder().id(id).build();
        UsuariosDeleteCommand command = deleteCommandMapping.toCommand(param);
        deleteCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
