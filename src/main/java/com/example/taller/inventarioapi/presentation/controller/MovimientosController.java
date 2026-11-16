package com.example.taller.inventarioapi.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import com.example.taller.inventarioapi.application.commands.MovimientosCreateCommand;
import com.example.taller.inventarioapi.application.commands.handlers.MovimientosCreateCommandHandler;
import com.example.taller.inventarioapi.application.dtos.MovimientosCreateRequest;
import com.example.taller.inventarioapi.application.dtos.MovimientosCreateResponse;
import com.example.taller.inventarioapi.application.dtos.MovimientosGetResponse;
import com.example.taller.inventarioapi.application.dtos.MovimientosListResponse;
import com.example.taller.inventarioapi.application.dtos.MovimientosPorProductoResponse;
import com.example.taller.inventarioapi.application.mapping.MovimientosCreateCommandMapping;
import com.example.taller.inventarioapi.application.parameters.MovimientosGetParam;
import com.example.taller.inventarioapi.application.parameters.MovimientosPorProductoParam;
import com.example.taller.inventarioapi.application.queries.MovimientosGetQuery;
import com.example.taller.inventarioapi.application.queries.MovimientosListarQuery;
import com.example.taller.inventarioapi.application.queries.MovimientosPorProductoQuery;
import com.example.taller.inventarioapi.application.queries.handler.MovimientosGetQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.MovimientosListarQueryHandler;
import com.example.taller.inventarioapi.application.queries.handler.MovimientosPorProductoQueryHandler;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movimientos")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class MovimientosController {
    private final MovimientosCreateCommandMapping createCommandMapping;
    private final MovimientosCreateCommandHandler createCommandHandler;
    private final MovimientosListarQueryHandler listarQueryHandler;
    private final MovimientosGetQueryHandler getQueryHandler;
    private final MovimientosPorProductoQueryHandler porProductoQueryHandler;

    @PostMapping
    public ResponseEntity<MovimientosCreateResponse> registrar(@Valid @RequestBody MovimientosCreateRequest request) {
        MovimientosCreateCommand command = createCommandMapping.toCommand(request);
        MovimientosCreateResponse response = createCommandHandler.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<MovimientosListResponse>> listar() {
        List<MovimientosListResponse> response = listarQueryHandler.execute(new MovimientosListarQuery());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientosPorProductoResponse>> listarPorProducto(
            @PathVariable Integer productoId) {
        MovimientosPorProductoParam param = new MovimientosPorProductoParam(productoId);
        MovimientosPorProductoQuery query = new MovimientosPorProductoQuery(param.getProductoId());
        return ResponseEntity.ok(porProductoQueryHandler.execute(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientosGetResponse> obtenerPorId(@PathVariable Integer id) {
        MovimientosGetParam param = new MovimientosGetParam(id);
        MovimientosGetResponse response = getQueryHandler.execute(new MovimientosGetQuery(param.getId()));
        return ResponseEntity.ok(response);
    }
}
