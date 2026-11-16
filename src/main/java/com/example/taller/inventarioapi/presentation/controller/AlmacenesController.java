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

import com.example.taller.inventarioapi.application.commands.AlmacenesCreateCommand;
import com.example.taller.inventarioapi.application.commands.AlmacenesDeleteCommand;
import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateCommand;
import com.example.taller.inventarioapi.application.commands.AlmacenesUpdateStatusCommand;
import com.example.taller.inventarioapi.application.commands.handlers.AlmacenesCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.AlmacenesDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.AlmacenesUpdateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.AlmacenesUpdateStatusCommandHandler;
import com.example.taller.inventarioapi.application.dtos.AlmacenesCreateRequest;
import com.example.taller.inventarioapi.application.dtos.AlmacenesCreateResponse;
import com.example.taller.inventarioapi.application.dtos.AlmacenesGetResponse;
import com.example.taller.inventarioapi.application.dtos.AlmacenesListResponse;
import com.example.taller.inventarioapi.application.dtos.AlmacenesUpdateRequest;
import com.example.taller.inventarioapi.application.dtos.AlmacenesUpdateStatusRequest;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.application.mapping.AlmacenesCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.AlmacenesCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.AlmacenesDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.AlmacenesUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.AlmacenesUpdateStatusCommandMapping;
import com.example.taller.inventarioapi.application.parameters.AlmacenesDeleteParam;
import com.example.taller.inventarioapi.application.parameters.AlmacenesGetParam;
import com.example.taller.inventarioapi.application.parameters.AlmacenesUpdateParam;
import com.example.taller.inventarioapi.application.parameters.AlmacenesUpdateStatusParam;
import com.example.taller.inventarioapi.application.queries.AlmacenesGetQuery;
import com.example.taller.inventarioapi.application.queries.AlmacenesListQuery;
import com.example.taller.inventarioapi.application.queries.handler.AlmacenesGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.AlmacenesListQueryHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/almacenes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AlmacenesController {
    private final AlmacenesCreateCommandMapping createCommandMapping;
    private final AlmacenesCreateCommandHandler createCommandHandler;
    private final AlmacenesCreateResponseMapping createResponseMapping;
    private final AlmacenesListQueryHandler listQueryHandler;
    private final AlmacenesGetQueryHandler getQueryHandler;
    private final AlmacenesUpdateCommandMapping updateCommandMapping;
    private final AlmacenesUpdateCommandHandler updateCommandHandler;
    private final AlmacenesUpdateStatusCommandMapping updateStatusCommandMapping;
    private final AlmacenesUpdateStatusCommandHandler updateStatusCommandHandler;
    private final AlmacenesDeleteCommandMapping deleteCommandMapping;
    private final AlmacenesDeleteCommandHandler deleteCommandHandler;

    @PostMapping
    public ResponseEntity<AlmacenesCreateResponse> crear(@Valid @RequestBody AlmacenesCreateRequest request) {
        AlmacenesCreateCommand command = createCommandMapping.toCommand(request);
        Almacen almacen = createCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createResponseMapping.toResponse(almacen));
    }

    @GetMapping
    public ResponseEntity<List<AlmacenesListResponse>> listar() {
        List<AlmacenesListResponse> response = listQueryHandler.execute(new AlmacenesListQuery());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlmacenesGetResponse> obtenerPorId(@PathVariable Integer id) {
        AlmacenesGetParam param = new AlmacenesGetParam(id);
        AlmacenesGetResponse response = getQueryHandler.execute(new AlmacenesGetQuery(param.getId()));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable Integer id,
            @Valid @RequestBody AlmacenesUpdateRequest request) {
        AlmacenesUpdateParam param = AlmacenesUpdateParam.builder().id(id).build();
        AlmacenesUpdateCommand command = updateCommandMapping.toCommand(param, request);
        updateCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @Valid @RequestBody AlmacenesUpdateStatusRequest request) {
        AlmacenesUpdateStatusParam param = AlmacenesUpdateStatusParam.builder().id(id).build();
        AlmacenesUpdateStatusCommand command = updateStatusCommandMapping.toCommand(param, request);
        updateStatusCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        AlmacenesDeleteParam param = AlmacenesDeleteParam.builder().id(id).build();
        AlmacenesDeleteCommand command = deleteCommandMapping.toCommand(param);
        deleteCommandHandler.execute(command);
        return ResponseEntity.noContent().build();
    }
}
