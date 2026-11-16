package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.ProveedoresDeleteCommand;
import com.example.taller.inventarioapi.application.parameters.ProveedoresDeleteParam;

@Component 
public class ProveedoresDeleteCommandMapping {
    public ProveedoresDeleteCommand toCommand(
            ProveedoresDeleteParam param) {

        return ProveedoresDeleteCommand.builder()
                .id(param.getId())
                .build();
    }
}
