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

import com.example.taller.inventarioapi.application.commands.ProductosCreateCommand;
import com.example.taller.inventarioapi.application.commands.ProductosDeleteCommand;
import com.example.taller.inventarioapi.application.commands.ProductosUpdateCommand;
import com.example.taller.inventarioapi.application.commands.ProductosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.commands.handlers.ProductosCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProductosDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProductosUpdateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProductosUpdateStatusCommandHandler;
import com.example.taller.inventarioapi.application.dtos.ProductosCreateRequest;
import com.example.taller.inventarioapi.application.dtos.ProductosCreateResponse;
import com.example.taller.inventarioapi.application.dtos.ProductosGetResponse;
import com.example.taller.inventarioapi.application.dtos.ProductosListResponse;
import com.example.taller.inventarioapi.application.dtos.ProductosUpdateRequest;
import com.example.taller.inventarioapi.application.dtos.ProductosUpdateStatusRequest;
import com.example.taller.inventarioapi.application.mapping.ProductosCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProductosCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.ProductosDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProductosUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProductosUpdateStatusCommandMapping;
import com.example.taller.inventarioapi.application.parameters.ProductosDeleteParam;
import com.example.taller.inventarioapi.application.parameters.ProductosGetParam;
import com.example.taller.inventarioapi.application.parameters.ProductosUpdateParam;
import com.example.taller.inventarioapi.application.parameters.ProductosUpdateStatusParam;
import com.example.taller.inventarioapi.application.queries.ProductosGetQuery;
import com.example.taller.inventarioapi.application.queries.ProductosListQuery;
import com.example.taller.inventarioapi.application.queries.handler.ProductosGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.ProductosListQueryHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/productos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class ProductosController {
    private final ProductosCreateCommandMapping createCommandMapping;
    private final ProductosCreateCommandHandler createCommandHandler;
    private final ProductosCreateResponseMapping createResponseMapping;
    private final ProductosListQueryHandler listQueryHandler;
    private final ProductosGetQueryHandler getQueryHandler;
    private final ProductosUpdateCommandMapping updateCommandMapping;
    private final ProductosUpdateCommandHandler updateCommandHandler;
    private final ProductosUpdateStatusCommandMapping updateStatusCommandMapping;
    private final ProductosUpdateStatusCommandHandler updateStatusCommandHandler;
    private final ProductosDeleteCommandMapping deleteCommandMapping;
    private final ProductosDeleteCommandHandler deleteCommandHandler;

    @PostMapping
    public ResponseEntity<ProductosCreateResponse> crear(@Valid @RequestBody ProductosCreateRequest request) {
        ProductosCreateCommand command = createCommandMapping.toCommand(request);
        Integer id = createCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseMapping.toResponse(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductosListResponse>> listar() {
        List<ProductosListResponse> response = listQueryHandler.execute(new ProductosListQuery());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductosGetResponse> obtenerPorId(@PathVariable Integer id) {
        ProductosGetParam param = new ProductosGetParam(id);
        ProductosGetResponse response = getQueryHandler.execute(new ProductosGetQuery(param.getId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody ProductosUpdateRequest request) {
        ProductosUpdateParam param = ProductosUpdateParam.builder().id(id).build();
        ProductosUpdateCommand command = updateCommandMapping.toCommand(param, request);
        updateCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody ProductosUpdateStatusRequest request) {
        ProductosUpdateStatusParam param = ProductosUpdateStatusParam.builder().id(id).build();
        ProductosUpdateStatusCommand command = updateStatusCommandMapping.toCommand(param, request);
        updateStatusCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ProductosDeleteParam param = ProductosDeleteParam.builder().id(id).build();
        ProductosDeleteCommand command = deleteCommandMapping.toCommand(param);
        deleteCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
