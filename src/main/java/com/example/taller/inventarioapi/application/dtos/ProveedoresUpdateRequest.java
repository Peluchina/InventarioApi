package com.example.taller.inventarioapi.application.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor 
@AllArgsConstructor 
@Builder 
public class ProveedoresUpdateRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 500, message = "El nombre no debe superar los 500 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "El contacto no debe superar los 500 caracteres")
    private String contacto;

    @Pattern(
        regexp = "^[0-9]{9}$",
        message = "El teléfono debe tener exactamente 9 dígitos"
    )
    private String telefono;

    @Email(message = "El correo electrónico no tiene un formato válido")
    @Size(max = 500, message = "El correo no debe superar los 500 caracteres")
    private String email;

    @Size(max = 500, message = "La dirección no debe superar los 500 caracteres")
    private String direccion;
}
