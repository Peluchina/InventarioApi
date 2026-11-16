package com.example.taller.inventarioapi.inventarioapi.config;

import java.util.List;

import com.example.taller.inventarioapi.infrastructure.security.CustomOAuth2UserService;
import com.example.taller.inventarioapi.infrastructure.security.CustomOidcUserService;
import com.example.taller.inventarioapi.infrastructure.security.OAuth2AuthenticationFailureHandler;
import com.example.taller.inventarioapi.infrastructure.security.OAuth2AuthenticationSuccessHandler;
import com.example.taller.inventarioapi.infrastructure.security.RolAutorizado;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;
    private final OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    SecurityConfig(
            CustomOAuth2UserService customOAuth2UserService,
            CustomOidcUserService customOidcUserService,
            OAuth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler,
            OAuth2AuthenticationFailureHandler oauth2AuthenticationFailureHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
        this.oauth2AuthenticationFailureHandler = oauth2AuthenticationFailureHandler;
        this.customOidcUserService = customOidcUserService;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
            .securityMatcher("/api/**")
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.GET, "/api/auth/**").hasAnyRole("ADMINISTRADOR", "ALMACENISTA")
                    .requestMatchers(HttpMethod.GET, "/api/proveedores", "/api/proveedores/**",
                            "/api/usuarios", "/api/usuarios/**",
                            "/api/almacenes", "/api/almacenes/**",
                            "/api/categorias", "/api/categorias/**",
                            "/api/productos", "/api/productos/**")
                    .hasAnyRole("ADMINISTRADOR", "ALMACENISTA")
                    .requestMatchers(HttpMethod.GET, "/api/movimientos", "/api/movimientos/**")
                    .hasAnyRole("ADMINISTRADOR", "ALMACENISTA")
                    .requestMatchers(HttpMethod.POST, "/api/movimientos", "/api/movimientos/**")
                    .hasAnyRole("ADMINISTRADOR", "ALMACENISTA")
                    // ADMINISTRADOR conserva acceso a todas las operaciones y rutas API.
                    .anyRequest().hasRole("ADMINISTRADOR"))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/v3/api-docs/**",
                    "/oauth2/**",
                    "/login/oauth2/**",
                    "/error"
                ).permitAll()
                // Esta ruta la consulta Angular por XHR; sin sesión debe responder 401,
                // no iniciar OAuth2 y redirigir la petición a Google.
                .requestMatchers("/auth/token").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth -> oauth
                .userInfoEndpoint(userInfo ->
                        userInfo.userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService))
                .successHandler(oauth2AuthenticationSuccessHandler)
                .failureHandler(oauth2AuthenticationFailureHandler)
            );

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter scopesConverter = new JwtGrantedAuthoritiesConverter();
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<GrantedAuthority> authorities =
                    new java.util.ArrayList<>(scopesConverter.convert(jwt));
            String role = jwt.getClaimAsString("rol");
            if (role != null && !role.isBlank()) {
                String normalizedRole = RolAutorizado.canonicalizar(role);
                if (RolAutorizado.soportado(normalizedRole)) {
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + normalizedRole));
                }
            }
            return authorities;
        });
        return converter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
