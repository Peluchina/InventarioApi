package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.dtos.ProveedoresCreateResponse;

@Component 
public class ProveedoresCreateResponseMapping {
     public ProveedoresCreateResponse toResponse(Integer id) {

        return ProveedoresCreateResponse.builder()
                .id(id)
                .mensaje("Proveedor registrado correctamente")
                .build();
    }
}
