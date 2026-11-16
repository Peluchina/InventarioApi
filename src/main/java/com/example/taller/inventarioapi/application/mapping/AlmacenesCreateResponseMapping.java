package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.AlmacenesCreateResponse;
import com.example.taller.inventarioapi.domain.aggregates.Almacen;

@Component
public class AlmacenesCreateResponseMapping {
    public AlmacenesCreateResponse toResponse(Almacen almacen) {
        return new AlmacenesCreateResponse(
                almacen.getId(), almacen.getNombre(), almacen.getUbicacion(), almacen.getEstadoId());
    }
}
