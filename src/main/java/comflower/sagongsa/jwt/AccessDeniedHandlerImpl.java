package comflower.sagongsa.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import comflower.sagongsa.response.ErrorResponse;
import comflower.sagongsa.exception.ExceptionType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var responseEntity = ErrorResponse.entity(ExceptionType.FORBIDDEN);
        response.setStatus(responseEntity.getStatusCode().value());
        objectMapper.writeValue(response.getOutputStream(), responseEntity.getBody());
    }
}
