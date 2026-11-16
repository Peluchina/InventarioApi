package com.example.taller.inventarioapi.infrastructure.security;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.time.Instant;

import com.example.taller.inventarioapi.inventarioapi.config.SecurityConfig;
import com.example.taller.inventarioapi.inventarioapi.InventarioApiApplication;
import com.example.taller.inventarioapi.presentation.controller.AuthMeController;
import com.example.taller.inventarioapi.presentation.controller.JwtTokenController;
import com.example.taller.inventarioapi.presentation.controller.SecurityProbeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;


@WebMvcTest(controllers = {
        SecurityProbeController.class,
        AuthMeController.class,
        JwtTokenController.class
})
@ContextConfiguration(classes = InventarioApiApplication.class)
@Import({SecurityConfig.class, CustomOAuth2UserService.class,
        CustomOidcUserService.class,
        OAuth2AuthenticationSuccessHandler.class,
        OAuth2AuthenticationFailureHandler.class})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JwtDecoder jwtDecoder;

    @MockitoBean
    private ClientRegistrationRepository clientRegistrationRepository;

    @MockitoBean
    private UsuarioSecurityRepository usuarioSecurityRepository;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void apiSinJwtResponde401() throws Exception {
        mockMvc.perform(get("/api/security-test"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void tokenSinSesionOAuthResponde401SinRedirigirAGoogle() throws Exception {
        mockMvc.perform(get("/auth/token"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> org.junit.jupiter.api.Assertions.assertNull(
                        result.getResponse().getRedirectedUrl()));
    }

    @Test
    void sesionOAuthPorSiSolaNoAutorizaLaApi() throws Exception {
        DefaultOAuth2User principal = new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")),
                Map.of("email", "usuario@example.com"),
                "email");
        OAuth2AuthenticationToken authentication = new OAuth2AuthenticationToken(
                principal, principal.getAuthorities(), "google");
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context);

        mockMvc.perform(get("/api/security-test").session(session))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void jwtInvalidoResponde401() throws Exception {
        when(jwtDecoder.decode("invalido"))
                .thenThrow(new BadJwtException("JWT inválido"));

        mockMvc.perform(get("/api/security-test")
                        .header("Authorization", "Bearer invalido"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void jwtValidoPermiteApiYSuRolSeConvierteEnAutoridad() throws Exception {
        when(jwtDecoder.decode("admin-valido")).thenReturn(jwt("admin-valido", "ADMINISTRADOR"));

        mockMvc.perform(get("/api/security-test/admin")
                        .header("Authorization", "Bearer admin-valido"))
                .andExpect(status().isOk());
    }

    @Test
    void claimAdminPersistidoComoAdminSeConvierteEnRolAdministrador() throws Exception {
        when(jwtDecoder.decode("admin-alias")).thenReturn(jwt("admin-alias", "ADMIN"));

        mockMvc.perform(get("/api/security-test/admin")
                        .header("Authorization", "Bearer admin-alias"))
                .andExpect(status().isOk());
    }

    @Test
    void jwtValidoDevuelveElUsuarioActual() throws Exception {
        when(jwtDecoder.decode("me-valido")).thenReturn(jwt("me-valido", "ADMINISTRADOR"));

        mockMvc.perform(get("/api/auth/me")
                        .header("Authorization", "Bearer me-valido"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(23))
                .andExpect(jsonPath("$.email").value("usuario@example.com"));
    }

    @Test
    void rolIncorrectoResponde403() throws Exception {
        when(jwtDecoder.decode("usuario-valido")).thenReturn(jwt("usuario-valido", "USUARIO"));

        mockMvc.perform(get("/api/security-test/admin")
                        .header("Authorization", "Bearer usuario-valido"))
                .andExpect(status().isForbidden());
    }

    @Test
    void administradorPuedeEjecutarTodasLasOperacionesDeLosModulos() throws Exception {
        String[][] operations = {
            {"GET", "/api/proveedores"}, {"POST", "/api/proveedores"},
            {"PUT", "/api/proveedores/1"}, {"PATCH", "/api/proveedores/1/estado"},
            {"DELETE", "/api/proveedores/1"},
            {"GET", "/api/usuarios"}, {"POST", "/api/usuarios"},
            {"PUT", "/api/usuarios/1"}, {"PATCH", "/api/usuarios/1/estado"},
            {"DELETE", "/api/usuarios/1"},
            {"GET", "/api/categorias"}, {"POST", "/api/categorias"},
            {"PUT", "/api/categorias/1"}, {"DELETE", "/api/categorias/1"},
            {"GET", "/api/almacenes"}, {"POST", "/api/almacenes"},
            {"PUT", "/api/almacenes/1"}, {"PATCH", "/api/almacenes/1/estado"},
            {"DELETE", "/api/almacenes/1"},
            {"GET", "/api/productos"}, {"POST", "/api/productos"},
            {"PUT", "/api/productos/1"}, {"PATCH", "/api/productos/1/estado"},
            {"DELETE", "/api/productos/1"},
            {"GET", "/api/movimientos"}, {"POST", "/api/movimientos"}
        };

        assertRoleCanAccess("ADMINISTRADOR", operations, true);
    }

    @Test
    void almacenistaSoloPuedeLeerModulosAdministrativosYGestionarMovimientos() throws Exception {
        String[][] permitted = {
            {"GET", "/api/proveedores"}, {"GET", "/api/proveedores/1"},
            {"GET", "/api/usuarios"}, {"GET", "/api/usuarios/1"},
            {"GET", "/api/categorias"}, {"GET", "/api/categorias/1"},
            {"GET", "/api/almacenes"}, {"GET", "/api/almacenes/1"},
            {"GET", "/api/productos"}, {"GET", "/api/productos/1"},
            {"GET", "/api/movimientos"}, {"GET", "/api/movimientos/1"},
            {"GET", "/api/movimientos/producto/1"}, {"POST", "/api/movimientos"}
        };
        String[][] denied = {
            {"POST", "/api/proveedores"}, {"PUT", "/api/proveedores/1"},
            {"PATCH", "/api/proveedores/1/estado"}, {"DELETE", "/api/proveedores/1"},
            {"POST", "/api/usuarios"}, {"PUT", "/api/usuarios/1"},
            {"PATCH", "/api/usuarios/1/estado"}, {"DELETE", "/api/usuarios/1"},
            {"POST", "/api/categorias"}, {"PUT", "/api/categorias/1"},
            {"DELETE", "/api/categorias/1"},
            {"POST", "/api/almacenes"}, {"PUT", "/api/almacenes/1"},
            {"PATCH", "/api/almacenes/1/estado"}, {"DELETE", "/api/almacenes/1"},
            {"POST", "/api/productos"}, {"PUT", "/api/productos/1"},
            {"PATCH", "/api/productos/1/estado"}, {"DELETE", "/api/productos/1"}
        };

        assertRoleCanAccess("ALMACENISTA", permitted, true);
        assertRoleCanAccess("ALMACENISTA", denied, false);
    }

    private void assertRoleCanAccess(String role, String[][] operations, boolean allowed)
            throws Exception {
        for (int i = 0; i < operations.length; i++) {
            String token = role.toLowerCase() + "-" + allowed + "-" + i;
            when(jwtDecoder.decode(token)).thenReturn(jwt(token, role));
            var result = mockMvc.perform(request(
                            HttpMethod.valueOf(operations[i][0]), operations[i][1])
                            .header("Authorization", "Bearer " + token))
                    .andReturn();
            if (allowed) {
                org.junit.jupiter.api.Assertions.assertNotEquals(403,
                        result.getResponse().getStatus(),
                        role + " debe acceder a " + operations[i][0] + " " + operations[i][1]);
            } else {
                org.junit.jupiter.api.Assertions.assertEquals(403,
                        result.getResponse().getStatus(),
                        role + " no debe acceder a " + operations[i][0] + " " + operations[i][1]);
            }
        }
    }

    @Test
    void swaggerYOpenApiSonPublicos() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(result -> assertNotEquals(401,
                        result.getResponse().getStatus()));
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(result -> assertNotEquals(401,
                        result.getResponse().getStatus()));
    }

    private static Jwt jwt(String value, String role) {
        Instant now = Instant.now();
        return Jwt.withTokenValue(value)
                .header("alg", "RS256")
                .issuer("inventario-api")
                .subject("23")
                .issuedAt(now.minusSeconds(5))
                .expiresAt(now.plusSeconds(600))
                .claim("email", "usuario@example.com")
                .claim("nombre", "Ana")
                .claim("apellidos", "García")
                .claim("rol", role)
                .build();
    }
}
