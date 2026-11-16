package com.example.taller.inventarioapi.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

class OAuth2AuthenticationSuccessHandlerTest {

    @Test
    void generaJwtYLoGuardaEnSesionAlCompletarOAuth() throws Exception {
        JwtService jwtService = mock(JwtService.class);
        OAuth2AuthenticationSuccessHandler handler =
                new OAuth2AuthenticationSuccessHandler(jwtService, "http://localhost:4200");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        OAuth2User principal = new DefaultOAuth2User(
                List.of(),
                Map.of("usuarioId", 7, "email", "usuario@example.com", "rol", "ADMIN",
                        "nombre", "Ana", "apellidos", "García"),
                "email");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(jwtService.generarToken(7, "usuario@example.com", "ADMINISTRADOR", "Ana", "García"))
                .thenReturn("jwt-generado");

        handler.onAuthenticationSuccess(request, response, authentication);

        assertEquals("http://localhost:4200/login", response.getRedirectedUrl());
        assertEquals("jwt-generado", request.getSession(false).getAttribute(
                OAuth2AuthenticationSuccessHandler.JWT_SESSION_ATTRIBUTE));
        verify(jwtService).generarToken(7, "usuario@example.com", "ADMINISTRADOR", "Ana", "García");
    }

    @Test
    void informaEnJsonSiElPrincipalNoTraeUsuarioLocal() throws Exception {
        OAuth2AuthenticationSuccessHandler handler =
                new OAuth2AuthenticationSuccessHandler(
                        mock(JwtService.class), "http://localhost:4200");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        OAuth2User principal = new DefaultOAuth2User(
                List.of(), Map.of("email", "usuario@example.com"), "email");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(principal);

        handler.onAuthenticationSuccess(request, response, authentication);

        assertEquals(302, response.getStatus());
        assertEquals("http://localhost:4200/login?error=login_failed",
                response.getRedirectedUrl());
        assertEquals("", response.getContentAsString());
    }
}
