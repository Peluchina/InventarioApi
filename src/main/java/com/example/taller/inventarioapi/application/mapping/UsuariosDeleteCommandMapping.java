package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.UsuariosDeleteCommand;
import com.example.taller.inventarioapi.application.parameters.UsuariosDeleteParam;

@Component
public class UsuariosDeleteCommandMapping {
    public UsuariosDeleteCommand toCommand(UsuariosDeleteParam param) {
        return UsuariosDeleteCommand.builder().id(param.getId()).build();
    }
}
