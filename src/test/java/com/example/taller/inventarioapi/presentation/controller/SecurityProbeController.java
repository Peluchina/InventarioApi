package com.example.taller.inventarioapi.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityProbeController {

    // Emula las rutas de los módulos para probar la política HTTP de SecurityConfig
    // sin inicializar los handlers de base de datos de los controladores reales.
    @RequestMapping(
            value = {
                "/api/proveedores", "/api/proveedores/**",
                "/api/usuarios", "/api/usuarios/**",
                "/api/almacenes", "/api/almacenes/**",
                "/api/productos", "/api/productos/**",
                "/api/movimientos", "/api/movimientos/**"
            },
            method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
                    RequestMethod.PATCH, RequestMethod.DELETE})
    String modulo() {
        return "ok";
    }

    @GetMapping("/api/security-test")
    String api() {
        return "ok";
    }

    @GetMapping("/api/security-test/admin")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    String admin() {
        return "admin";
    }
}
