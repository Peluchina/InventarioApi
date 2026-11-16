package com.example.taller.inventarioapi.domain.aggregates;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter 
@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 

public class Estado {
    private Integer id;
    private String nombre;
}
