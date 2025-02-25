package comflower.sagongsa.jwt;

import comflower.sagongsa.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthentication extends AbstractAuthenticationToken {
    private final User user;

    public JwtAuthentication(User user, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.user = user;
    }

    public static JwtAuthentication fromUser(User user) {
        var authorities = user.isManager() ?
                List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"))
                : List.of(new SimpleGrantedAuthority("USER"));
        return new JwtAuthentication(user, authorities);
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (!authenticated) {
            throw new IllegalArgumentException("Cannot set this token to untrusted - use JwtAuthenticationToken instead");
        }

        super.setAuthenticated(true);
    }
}
