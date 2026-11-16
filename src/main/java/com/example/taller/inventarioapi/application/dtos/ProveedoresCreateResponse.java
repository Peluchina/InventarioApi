package com.example.taller.inventarioapi.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@AllArgsConstructor 
@NoArgsConstructor 
@Builder 
public class ProveedoresCreateResponse {
    private Integer id;
    private String mensaje;
}
