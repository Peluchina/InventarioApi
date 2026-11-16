package com.example.taller.inventarioapi.application.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class ProveedoresUpdateCommand {
    private Integer id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
}
