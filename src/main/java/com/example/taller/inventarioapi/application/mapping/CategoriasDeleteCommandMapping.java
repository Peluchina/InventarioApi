package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.CategoriasDeleteCommand;
import com.example.taller.inventarioapi.application.parameters.CategoriasDeleteParam;

@Component
public class CategoriasDeleteCommandMapping {
    public CategoriasDeleteCommand toCommand(CategoriasDeleteParam param) {
        return CategoriasDeleteCommand.builder()
                .id(param.getId())
                .build();
    }
}
