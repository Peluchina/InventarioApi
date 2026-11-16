package com.example.taller.inventarioapi.presentation.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import com.example.taller.inventarioapi.infrastructure.security.OAuth2AuthenticationSuccessHandler;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.server.ResponseStatusException;

class JwtTokenControllerTest {

    @Test
    void devuelveElJwtGeneradoTrasLaAutenticacionOAuth() {
        JwtTokenController controller = new JwtTokenController();
        OAuth2User usuario = new DefaultOAuth2User(
                List.of(),
                Map.of("usuarioId", 23, "email", "usuario@example.com", "rol", "ADMIN"),
                "email");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute(
                OAuth2AuthenticationSuccessHandler.JWT_SESSION_ATTRIBUTE,
                "jwt-de-prueba");

        JwtTokenController.JwtTokenResponse respuesta =
                controller.obtenerToken(usuario, request);

        assertEquals("jwt-de-prueba", respuesta.accessToken());
        assertEquals("Bearer", respuesta.tokenType());
    }

    @Test
    void noEntregaJwtSinSesionOAuthValida() {
        JwtTokenController controller = new JwtTokenController();

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> controller.obtenerToken(null, new MockHttpServletRequest()));

        assertEquals(401, exception.getStatusCode().value());
    }
}
