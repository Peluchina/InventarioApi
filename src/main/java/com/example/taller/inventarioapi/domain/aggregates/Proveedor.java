package com.example.taller.inventarioapi.domain.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter 
@AllArgsConstructor
@NoArgsConstructor  
@Builder 
public class Proveedor {
    private Integer id;
    private String nombre;
    private String contacto;
    private String telefono;
    private String email;
    private String direccion;
    private Integer estadoId;
    
}
