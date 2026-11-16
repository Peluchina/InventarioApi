package com.example.taller.inventarioapi.application.queries.handler;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.mapping.ProveedoresGetResponseMapping;
import com.example.taller.inventarioapi.application.queries.ProveedoresGetQuery;
import com.example.taller.inventarioapi.domain.aggregates.Proveedor;
import com.example.taller.inventarioapi.domain.contracts.IProveedoresRepositoryWR;
import com.example.taller.inventarioapi.application.dtos.ProveedoresGetResponse;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProveedoresGetQueryHandler {
    private final IProveedoresRepositoryWR proveedoresRepository;
    private final ProveedoresGetResponseMapping responseMapping;

    public ProveedoresGetResponse  execute(ProveedoresGetQuery query) {

        Proveedor proveedor = proveedoresRepository
            .buscarPorId(query.getId())
            .orElse(null);

        return responseMapping.toResponse(proveedor);
    }
}
