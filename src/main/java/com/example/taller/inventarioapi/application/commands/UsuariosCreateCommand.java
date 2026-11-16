package com.example.taller.inventarioapi.application.commands;

import java.math.BigDecimal;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuariosCreateCommand {
    @Size(max = 500)
    private String nombre;
    @Size(max = 500)
    private String apellidos;
    private BigDecimal dni;
    @Size(max = 500)
    private String direccion;
    private Integer rolId;
    @Size(max = 500)
    private String email;
    @Size(max = 500)
    private String contrasenia;
}
