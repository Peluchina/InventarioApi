package com.example.taller.inventarioapi.infrastructure.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsuarioSecurityRepository usuarioRepository;

    public CustomOAuth2UserService(
            UsuarioSecurityRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        // Obtener usuario desde Google
        OAuth2User googleUser =
                new DefaultOAuth2UserService().loadUser(userRequest);

        // Reutilizar nuestra validación
        return validarUsuarioGoogle(googleUser);
    }

    public OAuth2User validarUsuarioGoogle(OAuth2User googleUser)
            throws OAuth2AuthenticationException {

        // 1. Obtener email de Google
        String email = googleUser.getAttribute("email");

        if (email == null || email.isBlank()) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("email_not_found"),
                    "No se pudo obtener el email de Google"
            );
        }

        // 2. Buscar usuario en MySQL
        UsuarioOAuth2 usuario =
                usuarioRepository.buscarPorEmail(email)
                        .orElseThrow(() ->
                                new OAuth2AuthenticationException(
                                        new OAuth2Error("user_not_registered"),
                                        "El usuario no está registrado"
                                )
                        );

        // 3. Verificar estado
        if (!Integer.valueOf(1).equals(usuario.getEstadoId())) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("user_inactive"),
                    "El usuario está inactivo"
            );
        }

        // 4. Verificar rol
        String rol = RolAutorizado.canonicalizar(usuario.getRol());
        if (usuario.getRolId() == null || !RolAutorizado.soportado(rol)) {

            throw new OAuth2AuthenticationException(
                    new OAuth2Error("user_without_role"),
                    "El usuario no tiene un rol asignado"
            );
        }

        // 5. Crear autoridad
        SimpleGrantedAuthority authority =
                new SimpleGrantedAuthority(
                        "ROLE_" + rol
                );

        Map<String, Object> attributes =
        new HashMap<>(googleUser.getAttributes());

        attributes.put("usuarioId", usuario.getId());
        attributes.put("nombre", usuario.getNombre());
        attributes.put("apellidos", usuario.getApellidos());
        attributes.put("rol", rol);

        return new DefaultOAuth2User(
                List.of(authority),
                attributes,
                "email"
        );
    }
}
