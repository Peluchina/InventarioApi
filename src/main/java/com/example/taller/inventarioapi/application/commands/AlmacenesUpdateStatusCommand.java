package com.example.taller.inventarioapi.application.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenesUpdateStatusCommand {
    @NotNull
    @Positive
    private Integer id;
    @NotNull
    @Positive
    private Integer estadoId;
}
