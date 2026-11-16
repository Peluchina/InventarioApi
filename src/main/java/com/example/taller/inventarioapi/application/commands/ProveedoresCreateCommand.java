package com.example.taller.inventarioapi.application.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@AllArgsConstructor 
@NoArgsConstructor  
@Builder 
public class ProveedoresCreateCommand {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 500)
    private String nombre;

    @Size(max = 500)
    private String contacto;

    @Pattern(
        regexp = "^[0-9]{9}$",
        message = "El teléfono debe tener 9 dígitos"
    )
    private String telefono;

    @Email(message = "El correo no es válido")
    @Size(max = 500)
    private String email;

    @Size(max = 500)
    private String direccion;
}
