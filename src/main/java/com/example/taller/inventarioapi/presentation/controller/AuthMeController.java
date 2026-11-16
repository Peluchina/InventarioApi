package com.example.taller.inventarioapi.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/auth")
@SecurityRequirement(name = "bearerAuth")
public class AuthMeController {

    @GetMapping("/me")
    public AuthMeResponse obtenerUsuarioActual(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Envía un JWT válido para consultar al usuario autenticado.");
        }

        Integer id;
        try {
            id = Integer.valueOf(jwt.getSubject());
        } catch (NumberFormatException exception) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "El JWT no contiene un identificador de usuario válido.");
        }

        String nombre = jwt.getClaimAsString("nombre");
        String apellidos = jwt.getClaimAsString("apellidos");
        return new AuthMeResponse(
                id,
                nombre,
                apellidos,
                jwt.getClaimAsString("email"));
    }

    public record AuthMeResponse(
            Integer id,
            String nombre,
            String apellidos,
            String email) {
    }
}
