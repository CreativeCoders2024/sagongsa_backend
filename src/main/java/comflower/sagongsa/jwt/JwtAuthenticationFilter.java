package comflower.sagongsa.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var bearerToken = request.getHeader("Authorization");
        if (bearerToken == null) {
            log.debug("Authorization header not provided");
            filterChain.doFilter(request, response);
            return;
        }

        if (!bearerToken.startsWith("Bearer ")) {
            log.debug("Malformed Authorization header provided");
            filterChain.doFilter(request, response);
            return;
        }

        var jwt = bearerToken.substring(7);

        var authenticationToken = new JwtAuthenticationToken(jwt);
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}