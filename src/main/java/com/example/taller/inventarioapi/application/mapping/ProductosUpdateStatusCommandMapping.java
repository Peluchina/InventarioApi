package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosUpdateStatusCommand;
import com.example.taller.inventarioapi.application.dtos.ProductosUpdateStatusRequest;
import com.example.taller.inventarioapi.application.parameters.ProductosUpdateStatusParam;

@Component
public class ProductosUpdateStatusCommandMapping {
    public ProductosUpdateStatusCommand toCommand(
            ProductosUpdateStatusParam param, ProductosUpdateStatusRequest request) {
        return ProductosUpdateStatusCommand.builder()
                .id(param.getId())
                .estadoId(request.getEstadoId())
                .build();
    }
}
