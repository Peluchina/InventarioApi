package com.example.taller.inventarioapi.application.commands;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosUpdateStatusCommand {
    @NotNull
    private Integer id;
    @NotNull
    private Integer estadoId;
}
