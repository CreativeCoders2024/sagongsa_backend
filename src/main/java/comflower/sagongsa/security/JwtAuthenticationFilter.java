package comflower.sagongsa.security;

import comflower.sagongsa.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtHelper jwtHelper;
    private final UserService userService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            logger.debug("Authorization header not provided");
            filterChain.doFilter(request, response);
            return;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            logger.debug("Malformed Authorization header provided");
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = bearerToken.substring(7);
        var claims = jwtHelper.parse(jwt);
        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }

        var userId = Long.valueOf(claims.getPayload().getSubject());
        var user = userService.findUserById(userId);
        SecurityContextHolder.getContext().setAuthentication(new UserAuthentication(user));

        filterChain.doFilter(request, response);
    }
}