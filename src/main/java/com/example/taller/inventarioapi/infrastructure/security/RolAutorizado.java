package com.example.taller.inventarioapi.infrastructure.security;

import java.util.Locale;

/** Maps persisted role names to the canonical authorities understood by Spring Security. */
public final class RolAutorizado {
    private RolAutorizado() {}

    public static String canonicalizar(String rol) {
        if (rol == null || rol.isBlank()) return null;
        String normalized = rol.trim().toUpperCase(Locale.ROOT);
        if (normalized.startsWith("ROLE_")) normalized = normalized.substring(5);
        return switch (normalized) {
            case "ADMIN", "ADMINISTRADOR" -> "ADMINISTRADOR";
            case "ALMACENISTA" -> "ALMACENISTA";
            default -> normalized;
        };
    }

    public static boolean soportado(String rol) {
        return "ADMINISTRADOR".equals(rol) || "ALMACENISTA".equals(rol);
    }
}
