package comflower.sagongsa.jwt;

import comflower.sagongsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    @Override
    public JwtAuthentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken)) {
            log.error("Unsupported authentication type: {}", authentication.getClass());
            return null;
        }

        var jwt = jwtAuthenticationToken.getCredentials();
        var claims = jwtHelper.parse(jwt);
        var subject = claims.getPayload().getSubject();

        var user = userRepository.findById(Long.valueOf(subject));
        if (user.isEmpty()) {
            log.debug("User not found from valid JWT: {}", subject);
            return null;
        }

        log.debug("Authenticated user: {}", subject);
        return JwtAuthentication.fromUser(user.get());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
