package org.erusakov.backend.config.security.authToken;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;
    private final String token;

    public JwtAuthenticationToken(String principal, String token) {
        super(null);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

}
