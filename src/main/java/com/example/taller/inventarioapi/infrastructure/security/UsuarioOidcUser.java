package com.example.taller.inventarioapi.infrastructure.security;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

/** Preserves Google's OIDC data while adding validated local user attributes. */
final class UsuarioOidcUser implements OidcUser {

    private final OidcUser delegate;
    private final OAuth2User validatedLocalUser;
    private final Map<String, Object> attributes;

    UsuarioOidcUser(OidcUser delegate, OAuth2User validatedLocalUser) {
        this.delegate = delegate;
        this.validatedLocalUser = validatedLocalUser;
        Map<String, Object> mergedAttributes = new LinkedHashMap<>(delegate.getAttributes());
        mergedAttributes.putAll(validatedLocalUser.getAttributes());
        this.attributes = Collections.unmodifiableMap(mergedAttributes);
    }

    @Override
    public Map<String, Object> getClaims() {
        return delegate.getClaims();
    }

    @Override
    public OidcIdToken getIdToken() {
        return delegate.getIdToken();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return delegate.getUserInfo();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return validatedLocalUser.getAuthorities();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }
}
