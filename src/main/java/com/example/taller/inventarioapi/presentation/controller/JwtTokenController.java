package com.example.taller.inventarioapi.presentation.controller;

import com.example.taller.inventarioapi.infrastructure.security.OAuth2AuthenticationSuccessHandler;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class JwtTokenController {

    @GetMapping("/token")
    public JwtTokenResponse obtenerToken(
            @AuthenticationPrincipal OAuth2User usuario,
            HttpServletRequest request) {

        if (usuario == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Inicia sesión con Google antes de solicitar un JWT.");
        }

        Integer usuarioId = usuario.getAttribute("usuarioId");
        String email = usuario.getAttribute("email");
        String rol = usuario.getAttribute("rol");

        if (usuarioId == null || email == null || rol == null || rol.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "La sesión no contiene un usuario local válido.");
        }

        HttpSession session = request.getSession(false);
        String accessToken = session == null
                ? null
                : (String) session.getAttribute(
                        OAuth2AuthenticationSuccessHandler.JWT_SESSION_ATTRIBUTE);
        if (accessToken == null || accessToken.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "La sesión no tiene un JWT vigente. Vuelve a iniciar sesión con Google.");
        }

        return new JwtTokenResponse(accessToken, "Bearer");
    }

    public record JwtTokenResponse(String accessToken, String tokenType) {
    }
}
