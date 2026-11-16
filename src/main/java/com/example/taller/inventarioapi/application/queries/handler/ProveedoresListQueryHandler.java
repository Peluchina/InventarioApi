package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProveedoresListResponse;
import com.example.taller.inventarioapi.application.mapping.ProveedoresListResponseMapping;
import com.example.taller.inventarioapi.application.queries.ProveedoresListQuery;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProveedoresListQueryHandler {
    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresListResponseMapping responseMapping;

    public List<ProveedoresListResponse> execute(ProveedoresListQuery query) {

        return proveedoresRepository.listar()
                .stream()
                .map(responseMapping::toResponse)
                .toList();
    }
}
