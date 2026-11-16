package com.example.taller.inventarioapi.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

class CustomOidcUserServiceTest {

    private static final String EMAIL = "usuario@example.com";

    @Test
    void oidcAgregaLosAtributosLocalesYValidaElUsuarioEnMySql() {
        UsuarioSecurityRepository repository = mock(UsuarioSecurityRepository.class);
        when(repository.buscarPorEmail(EMAIL)).thenReturn(Optional.of(
                new UsuarioOAuth2(23, "Ana", "García", EMAIL,
                        1, "ADMIN", 1)));
        CustomOAuth2UserService validator = new CustomOAuth2UserService(repository);

        OidcUser googleUser = mock(OidcUser.class);
        when(googleUser.getAttribute("email")).thenReturn(EMAIL);
        when(googleUser.getAttributes()).thenReturn(Map.of(
                "sub", "google-user-id", "email", EMAIL));
        when(googleUser.getClaims()).thenReturn(Map.of(
                "sub", "google-user-id", "email", EMAIL));
        when(googleUser.getAuthorities()).thenReturn(List.of());
        when(googleUser.getName()).thenReturn("google-user-id");
        OidcUserRequest request = mock(OidcUserRequest.class);

        CustomOidcUserService service =
                new CustomOidcUserService(validator, ignored -> googleUser);
        OidcUser authenticated = service.loadUser(request);

        assertEquals(Integer.valueOf(23), authenticated.getAttribute("usuarioId"));
        assertEquals(EMAIL, authenticated.getAttribute("email"));
        assertEquals("ADMINISTRADOR", authenticated.getAttribute("rol"));
        assertEquals(new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"),
                authenticated.getAuthorities().iterator().next());
    }
}
