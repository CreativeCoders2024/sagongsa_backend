package comflower.sagongsa.auth;

import comflower.sagongsa.auth.jwt.JwtHelper;
import comflower.sagongsa.auth.request.LoginRequest;
import comflower.sagongsa.auth.request.SignupRequest;
import comflower.sagongsa.auth.response.AuthenticatedResponse;
import comflower.sagongsa.user.User;
import comflower.sagongsa.user.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtHelper jwtHelper;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthenticatedResponse signup(@RequestBody SignupRequest request) {
        User user = authService.signup(request);
        String jwt = jwtHelper.generateToken(user.getId());
        return AuthenticatedResponse.builder().user(new UserResponse(user)).token(jwt).build();
    }

    @PostMapping("/login")
    public AuthenticatedResponse login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        String jwt = jwtHelper.generateToken(user.getId());
        return AuthenticatedResponse.builder().user(new UserResponse(user)).token(jwt).build();
    }
}
