package com.example.taller.inventarioapi.infrastructure.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationFailureHandler.class);
    private final String frontendUrl;

    public OAuth2AuthenticationFailureHandler(
            @Value("${app.frontend-url:http://localhost:4200}") String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();

        String errorCode = codigoSeguro(exception);
        logger.warn("Falló el inicio de sesión OAuth2: código={}, tipo={}",
                errorCode, exception.getClass().getSimpleName());
        response.sendRedirect(frontendUrl.replaceAll("/+$", "") + "/login?error=" + errorCode);
    }

    private String codigoSeguro(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException oauthException) {
            String code = oauthException.getError().getErrorCode();
            if ("user_not_registered".equals(code)) return code;
            if ("user_inactive".equals(code)) return code;
        }
        return "login_failed";
    }
}
