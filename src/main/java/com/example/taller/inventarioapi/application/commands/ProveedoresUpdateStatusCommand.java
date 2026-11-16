package com.example.taller.inventarioapi.application.commands;

import jakarta.validation.constraints.NotNull;
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
public class ProveedoresUpdateStatusCommand {
    @NotNull
    private Integer id;
    
    @NotNull
    private Integer estadoId;
}
