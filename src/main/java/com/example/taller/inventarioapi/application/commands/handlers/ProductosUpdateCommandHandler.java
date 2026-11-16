package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosUpdateCommand;
import com.example.taller.inventarioapi.application.validators.ProductosUpdateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Producto;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosUpdateCommandHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosUpdateCommandValidator validator;

    public void execute(ProductosUpdateCommand command) {
        validator.validate(command);
        Producto producto = Producto.builder()
                .id(command.getId())
                .sku(command.getSku())
                .nombre(command.getNombre())
                .descripcion(command.getDescripcion())
                .categoriaId(command.getCategoriaId())
                .precioCompra(command.getPrecioCompra())
                .precioVenta(command.getPrecioVenta())
                .stockMinimo(command.getStockMinimo())
                .build();
        productosRepository.actualizar(producto);
    }
}
