package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.example.taller.inventarioapi.application.dtos.AlmacenesGetResponse;
import com.example.taller.inventarioapi.application.mapping.AlmacenesGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.AlmacenesGetQuery;
import com.example.taller.inventarioapi.application.validators.AlmacenesGetQueryValidator;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesGetQueryHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesGetResponseMapping responseMapping;
    private final AlmacenesGetQueryValidator validator;

    public AlmacenesGetResponse execute(AlmacenesGetQuery query) {
        validator.validate(query);
        Almacen almacen = almacenesRepository.buscarPorId(query.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Almacén no encontrado"));
        return responseMapping.toResponse(almacen);
    }
}
