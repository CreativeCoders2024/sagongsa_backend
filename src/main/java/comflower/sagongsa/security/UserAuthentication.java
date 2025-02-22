package comflower.sagongsa.security;

import comflower.sagongsa.entity.User;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class UserAuthentication extends AbstractAuthenticationToken {
    private final User user;

    public UserAuthentication(User user) {
        super(user.isManager() ?
                List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"))
                : List.of(new SimpleGrantedAuthority("USER")));
        this.user = user;
    }

    @Override
    public User getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
