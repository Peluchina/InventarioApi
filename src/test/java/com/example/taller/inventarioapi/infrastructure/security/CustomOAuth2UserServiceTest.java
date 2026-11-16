package com.example.taller.inventarioapi.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class CustomOAuth2UserServiceTest {

    private static final String EMAIL = "usuario@example.com";

    private UsuarioSecurityRepository repository;
    private CustomOAuth2UserService service;

    @BeforeEach
    void setUp() {
        repository = mock(UsuarioSecurityRepository.class);
        service = new CustomOAuth2UserService(repository);
    }

    @Test
    void aceptaUsuarioRegistradoActivoConRol() {
        when(repository.buscarPorEmail(EMAIL)).thenReturn(Optional.of(
                usuario(10, "ADMIN", 1)));

        OAuth2User authenticated = service.validarUsuarioGoogle(googleUser());

        assertEquals(EMAIL, authenticated.getAttribute("email"));
        assertEquals(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"),
                authenticated.getAuthorities().iterator().next());
        verify(repository).buscarPorEmail(EMAIL);
    }

    @Test
    void rechazaUsuarioNoRegistrado() {
        when(repository.buscarPorEmail(EMAIL)).thenReturn(Optional.empty());

        OAuth2AuthenticationException exception = assertThrows(
                OAuth2AuthenticationException.class,
                () -> service.validarUsuarioGoogle(googleUser()));

        assertEquals("user_not_registered",
                exception.getError().getErrorCode());
    }

    @Test
    void rechazaUsuarioInactivo() {
        when(repository.buscarPorEmail(EMAIL)).thenReturn(Optional.of(
                usuario(10, "ADMIN", 2)));

        OAuth2AuthenticationException exception = assertThrows(
                OAuth2AuthenticationException.class,
                () -> service.validarUsuarioGoogle(googleUser()));

        assertEquals("user_inactive", exception.getError().getErrorCode());
    }

    @Test
    void rechazaUsuarioSinRol() {
        when(repository.buscarPorEmail(EMAIL)).thenReturn(Optional.of(
                new UsuarioOAuth2(1, "Nombre", "Apellido", EMAIL,
                        null, null, 1)));

        OAuth2AuthenticationException exception = assertThrows(
                OAuth2AuthenticationException.class,
                () -> service.validarUsuarioGoogle(googleUser()));

        assertEquals("user_without_role",
                exception.getError().getErrorCode());
    }

    private static UsuarioOAuth2 usuario(Integer rolId, String rol, Integer estadoId) {
        return new UsuarioOAuth2(1, "Nombre", "Apellido", EMAIL,
                rolId, rol, estadoId);
    }

    private static OAuth2User googleUser() {
        return new DefaultOAuth2User(
                java.util.List.of(),
                Map.of("email", EMAIL),
                "email");
    }
}
