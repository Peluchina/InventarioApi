package com.example.taller.inventarioapi.presentation.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.taller.inventarioapi.application.commands.ProveedoresCreateCommand;
import com.example.taller.inventarioapi.application.commands.ProveedoresDeleteCommand;
import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateCommand;
import com.example.taller.inventarioapi.application.commands.ProveedoresUpdateStatusCommand;
import com.example.taller.inventarioapi.application.commands.handlers.ProveedoresCreateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProveedoresDeleteCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProveedoresUpdateCommandHandler;
import com.example.taller.inventarioapi.application.commands.handlers.ProveedoresUpdateStatusCommandHandler;
import com.example.taller.inventarioapi.application.dtos.ProveedoresCreateRequest;
import com.example.taller.inventarioapi.application.dtos.ProveedoresCreateResponse;
import com.example.taller.inventarioapi.application.dtos.ProveedoresGetResponse;
import com.example.taller.inventarioapi.application.dtos.ProveedoresListResponse;
import com.example.taller.inventarioapi.application.dtos.ProveedoresUpdateRequest;
import com.example.taller.inventarioapi.application.dtos.ProveedoresUpdateStatusRequest;
import com.example.taller.inventarioapi.application.mapping.ProveedoresCreateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProveedoresCreateResponseMapping;
import com.example.taller.inventarioapi.application.mapping.ProveedoresDeleteCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProveedoresUpdateCommandMapping;
import com.example.taller.inventarioapi.application.mapping.ProveedoresUpdateStatusCommandMapping;
import com.example.taller.inventarioapi.application.parameters.ProveedoresDeleteParam;
import com.example.taller.inventarioapi.application.parameters.ProveedoresGetParam;
import com.example.taller.inventarioapi.application.parameters.ProveedoresUpdateParam;
import com.example.taller.inventarioapi.application.parameters.ProveedoresUpdateStatusParam;
import com.example.taller.inventarioapi.application.queries.ProveedoresGetQuery;
import com.example.taller.inventarioapi.application.queries.ProveedoresListQuery;
import com.example.taller.inventarioapi.application.queries.handler.ProveedoresGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.ProveedoresListQueryHandler;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController 
@RequestMapping ("/api/proveedores")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor 
public class ProveedoresController {

    private final ProveedoresCreateCommandMapping commandMapping;
    private final ProveedoresCreateCommandHandler commandHandler;
    private final ProveedoresCreateResponseMapping responseMapping;
    private final ProveedoresListQueryHandler listarHandler;
    private final ProveedoresGetQueryHandler getHandler;
    private final ProveedoresUpdateCommandMapping updateCommandMapping;
    private final ProveedoresUpdateCommandHandler updateCommandHandler;
    private final ProveedoresUpdateStatusCommandMapping updateStatusCommandMapping;
    private final ProveedoresUpdateStatusCommandHandler updateStatusCommandHandler;
    private final ProveedoresDeleteCommandMapping deleteCommandMapping;
    private final ProveedoresDeleteCommandHandler deleteCommandHandler;

    @PostMapping
    public ResponseEntity<ProveedoresCreateResponse>crear(
        @Valid @RequestBody ProveedoresCreateRequest request){
            ProveedoresCreateCommand command=commandMapping.toCommand(request);
            Integer id=commandHandler.execute(command);
            ProveedoresCreateResponse  response= responseMapping.toResponse(id); 
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        
    @GetMapping
    public ResponseEntity<List<ProveedoresListResponse>> listar() {

        ProveedoresListQuery query = new ProveedoresListQuery();

        List<ProveedoresListResponse> response = listarHandler.execute(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProveedoresGetResponse> obtenerPorId(@PathVariable Integer id) {

        
        ProveedoresGetParam param = new ProveedoresGetParam(id);

        ProveedoresGetQuery query =
                new ProveedoresGetQuery(param.getId());

        ProveedoresGetResponse response = getHandler.execute(query);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizar(
            @PathVariable Integer id,
            @RequestBody ProveedoresUpdateRequest request) {

        ProveedoresUpdateParam param =
                ProveedoresUpdateParam.builder()
                        .id(id)
                        .build();

        ProveedoresUpdateCommand command =
                updateCommandMapping.toCommand(param, request);

        updateCommandHandler.execute(command);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping ("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
        @PathVariable Integer id,
        @RequestBody ProveedoresUpdateStatusRequest request) {

        ProveedoresUpdateStatusParam param =
                ProveedoresUpdateStatusParam.builder()
                        .id(id)
                        .build();

        ProveedoresUpdateStatusCommand command =
        updateStatusCommandMapping.toCommand(param, request);

        updateStatusCommandHandler.execute(command);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        ProveedoresDeleteParam param =
                ProveedoresDeleteParam.builder()
                        .id(id)
                        .build();

        ProveedoresDeleteCommand command =
                deleteCommandMapping.toCommand(param);

        deleteCommandHandler.execute(command);

        return ResponseEntity.noContent().build();
    }
}
