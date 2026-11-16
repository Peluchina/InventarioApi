package com.example.taller.inventarioapi.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriasCreateRequest {
    @NotBlank
    @Size(max = 500)
    private String nombre;

    @Size(max = 500)
    private String descripcion;
}
