package com.example.taller.inventarioapi.application.commands.handlers;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosCreateCommand;
import com.example.taller.inventarioapi.application.validators.ProductosCreateCommandValidator;
import com.example.taller.inventarioapi.domain.aggregates.Producto;
import com.example.taller.inventarioapi.domain.contracts.IProductosRepositoryWR;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductosCreateCommandHandler {
    private final IProductosRepositoryWR productosRepository;
    private final ProductosCreateCommandValidator validator;

    public Integer execute(ProductosCreateCommand command) {
        validator.validate(command);
        Producto producto = Producto.builder()
                .sku(command.getSku())
                .nombre(command.getNombre())
                .descripcion(command.getDescripcion())
                .categoriaId(command.getCategoriaId())
                .precioCompra(command.getPrecioCompra())
                .precioVenta(command.getPrecioVenta())
                .stockMinimo(command.getStockMinimo())
                .stockActual(command.getStockActual())
                .build();
        return productosRepository.guardar(producto);
    }
}
