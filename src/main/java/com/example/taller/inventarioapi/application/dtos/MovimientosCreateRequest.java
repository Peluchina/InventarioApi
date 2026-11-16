package com.example.taller.inventarioapi.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimientosCreateRequest {
    @NotNull
    private Integer productoId;
    @NotBlank
    @Size(max = 20)
    private String tipoMovimiento;
    @NotNull
    private Integer cantidad;
    @Size(max = 500)
    private String referencia;
    @NotNull
    private Integer usuarioId;
}
