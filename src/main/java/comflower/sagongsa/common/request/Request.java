package comflower.sagongsa.common.request;

import org.springframework.validation.Errors;

public interface Request {
    void validate(Errors errors);
}
