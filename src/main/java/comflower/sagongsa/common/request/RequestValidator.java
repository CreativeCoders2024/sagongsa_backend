package comflower.sagongsa.common.request;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RequestValidator implements Validator {
    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        var request = (Request) target;
        request.validate(errors);
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return Request.class.isAssignableFrom(clazz);
    }
}
