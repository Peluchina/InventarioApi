package com.example.taller.inventarioapi.application.queries.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.AlmacenesListResponse;
import com.example.taller.inventarioapi.application.mapping.AlmacenesListResponseMapping;
import com.example.taller.inventarioapi.application.queries.AlmacenesListQuery;
import com.example.taller.inventarioapi.domain.contracts.IAlmacenesRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AlmacenesListQueryHandler {
    private final IAlmacenesRepositoryWR almacenesRepository;
    private final AlmacenesListResponseMapping responseMapping;

    public List<AlmacenesListResponse> execute(AlmacenesListQuery query) {
        return almacenesRepository.listar().stream().map(responseMapping::toResponse).toList();
    }
}
