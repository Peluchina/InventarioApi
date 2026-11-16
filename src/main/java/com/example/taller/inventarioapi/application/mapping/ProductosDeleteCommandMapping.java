package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProductosDeleteCommand;
import com.example.taller.inventarioapi.application.parameters.ProductosDeleteParam;

@Component
public class ProductosDeleteCommandMapping {
    public ProductosDeleteCommand toCommand(ProductosDeleteParam param) {
        return ProductosDeleteCommand.builder().id(param.getId()).build();
    }
}
