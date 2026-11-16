package com.example.taller.inventarioapi.infrastructure.security;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

class OAuth2AuthenticationFailureHandlerTest {

    @Test
    void usuarioNoRegistradoRecibe401YSeInvalidaSesionAnterior() throws Exception {
        OAuth2AuthenticationFailureHandler handler =
                new OAuth2AuthenticationFailureHandler("http://localhost:4200");
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockHttpSession previousSession = new MockHttpSession();
        request.setSession(previousSession);

        handler.onAuthenticationFailure(
                request,
                response,
                new OAuth2AuthenticationException(
                        new OAuth2Error("user_not_registered")));

        assertEquals(302, response.getStatus());
        assertEquals("http://localhost:4200/login?error=user_not_registered",
                response.getRedirectedUrl());
        assertEquals("", response.getContentAsString());
        assertEquals(true, previousSession.isInvalid());
    }
}
