package comflower.sagongsa.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import comflower.sagongsa.exception.ExceptionType;
import comflower.sagongsa.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var responseEntity = ErrorResponse.entity(ExceptionType.UNAUTHORIZED);
        response.setStatus(responseEntity.getStatusCode().value());
        objectMapper.writeValue(response.getOutputStream(), responseEntity.getBody());
    }
}
