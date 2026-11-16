package com.example.taller.inventarioapi.application.mapping;

import org.springframework.stereotype.Component;

import com.example.taller.inventarioapi.application.commands.AlmacenesDeleteCommand;
import com.example.taller.inventarioapi.application.parameters.AlmacenesDeleteParam;

@Component
public class AlmacenesDeleteCommandMapping {
    public AlmacenesDeleteCommand toCommand(AlmacenesDeleteParam param) {
        return AlmacenesDeleteCommand.builder().id(param.getId()).build();
    }
}
