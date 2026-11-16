package com.example.taller.inventarioapi.infrastructure.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    public static final String JWT_SESSION_ATTRIBUTE = "inventario.jwt.accessToken";

    private final JwtService jwtService;
    private final String frontendUrl;

    public OAuth2AuthenticationSuccessHandler(
            JwtService jwtService,
            @Value("${app.frontend-url:http://localhost:4200}") String frontendUrl) {
        this.jwtService = jwtService;
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        if (!(authentication.getPrincipal() instanceof OAuth2User usuario)) {
            responderNoAutorizado(request, response);
            return;
        }

        Integer usuarioId = usuario.getAttribute("usuarioId");
        String email = usuario.getAttribute("email");
        String rol = RolAutorizado.canonicalizar(usuario.getAttribute("rol"));
        if (usuarioId == null || email == null || !RolAutorizado.soportado(rol)) {
            responderNoAutorizado(request, response);
            return;
        }

        String accessToken = jwtService.generarToken(
                usuarioId,
                email,
                rol,
                usuario.getAttribute("nombre"),
                usuario.getAttribute("apellidos"));
        request.getSession(true).setAttribute(JWT_SESSION_ATTRIBUTE, accessToken);
        response.sendRedirect(frontendUrl.replaceAll("/+$", "") + "/login");
    }

    private void responderNoAutorizado(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        var session = request.getSession(false);
        if (session != null) session.invalidate();
        response.sendRedirect(frontendUrl.replaceAll("/+$", "") + "/login?error=login_failed");
    }
}
