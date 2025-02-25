package comflower.sagongsa.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private final String jwt;

    public JwtAuthenticationToken(String jwt) {
        super(null);
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public String getCredentials() {
        return jwt;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use JwtAuthentication instead");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        throw new IllegalArgumentException("Cannot erase credentials of this token - use JwtAuthentication instead");
    }
}
