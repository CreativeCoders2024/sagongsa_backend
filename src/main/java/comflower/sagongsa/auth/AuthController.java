package comflower.sagongsa.auth;

import comflower.sagongsa.auth.jwt.JwtHelper;
import comflower.sagongsa.auth.request.LoginRequest;
import comflower.sagongsa.auth.request.SignupRequest;
import comflower.sagongsa.auth.response.AuthenticatedResponse;
import comflower.sagongsa.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtHelper jwtHelper;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticatedResponse> signup(@RequestBody SignupRequest request) {
        User user = authService.signup(request);
        String jwt = jwtHelper.generateToken(user.getId());
        return ResponseEntity
                .created(URI.create(String.valueOf(user.getId())))  // header - Location에 추가해줌
                .body(AuthenticatedResponse.builder().user(user).token(jwt).build());
    }

    @PostMapping("/login")
    public AuthenticatedResponse login(@RequestBody LoginRequest request) {
        User user = authService.login(request);
        String jwt = jwtHelper.generateToken(user.getId());
        return AuthenticatedResponse.builder().user(user).token(jwt).build();
    }
}
