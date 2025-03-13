package comflower.sagongsa.request;

import org.springframework.validation.Errors;

public interface Request {
    void validate(Errors errors);
}
