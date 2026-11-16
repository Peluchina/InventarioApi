package com.example.taller.inventarioapi.application.commands;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenesUpdateCommand {
    @NotNull
    @Positive
    private Integer id;
    @NotBlank
    @Size(max = 500)
    private String nombre;
    @NotBlank
    @Size(max = 500)
    private String ubicacion;
}
