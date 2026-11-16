package com.example.taller.inventarioapi.infrastructure.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Applies MySQL allow-list validation to Google's OpenID Connect login. */
@Service
public class CustomOidcUserService
        implements OAuth2UserService<OidcUserRequest, OidcUser> {

    private final CustomOAuth2UserService localUserValidator;
    private final OAuth2UserService<OidcUserRequest, OidcUser> delegate;

    @Autowired
    public CustomOidcUserService(CustomOAuth2UserService localUserValidator) {
        this(localUserValidator, new OidcUserService());
    }

    CustomOidcUserService(
            CustomOAuth2UserService localUserValidator,
            OAuth2UserService<OidcUserRequest, OidcUser> delegate) {
        this.localUserValidator = localUserValidator;
        this.delegate = delegate;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest)
            throws OAuth2AuthenticationException {
        OidcUser googleUser = delegate.loadUser(userRequest);
        var validatedUser = localUserValidator.validarUsuarioGoogle(googleUser);
        return new UsuarioOidcUser(googleUser, validatedUser);
    }
}
