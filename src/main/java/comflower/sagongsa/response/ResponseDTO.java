package comflower.sagongsa.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<T> {
    private HttpStatus status;
    private String message;
    private T data;
}
