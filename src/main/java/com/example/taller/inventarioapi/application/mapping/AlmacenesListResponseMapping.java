package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.AlmacenesListResponse;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;

@Component
public class AlmacenesListResponseMapping {
    public AlmacenesListResponse toResponse(Almacen almacen) {
        return new AlmacenesListResponse(
                almacen.getId(), almacen.getNombre(), almacen.getUbicacion(), almacen.getEstadoId());
    }
}
