package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.AlmacenesGetResponse;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;

@Component
public class AlmacenesGetResponseMapping {
    public AlmacenesGetResponse toResponse(Almacen almacen) {
        return new AlmacenesGetResponse(
                almacen.getId(), almacen.getNombre(), almacen.getUbicacion(), almacen.getEstadoId());
    }
}
