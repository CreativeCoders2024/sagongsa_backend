package comflower.sagongsa.controller;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.jwt.JwtHelper;
import comflower.sagongsa.request.EditUserRequest;
import comflower.sagongsa.request.LoginRequest;
import comflower.sagongsa.request.SignupRequest;
import comflower.sagongsa.response.SignupResponse;
import comflower.sagongsa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "user")
public class UserController {
    private final UserService userService;
    private final JwtHelper jwtHelper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        User user = userService.createUser(request);
        String jwt = jwtHelper.generateToken(user.getId());

        return ResponseEntity
                .created(URI.create(String.valueOf(user.getId())))  // header - Location에 추가해줌
                .body(
                        SignupResponse.builder()
                                .userId(user.getId())
                                .token(jwt)
                                .build()
                );
    }

    @PostMapping("/login")
    public SignupResponse login(@RequestBody LoginRequest request) {
        User user = userService.loginUser(request);
        String jwt = jwtHelper.generateToken(user.getId());

        return SignupResponse.builder()
                .userId(user.getId())
                .token(jwt)
                .build();
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public User getUserSelf(@AuthenticationPrincipal User user) {
        return user;
    }

    @PutMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public User editUser(@AuthenticationPrincipal User user, @RequestBody EditUserRequest request) {
        return userService.editUser(user, request);
    }

    @DeleteMapping("/users/@me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
    }
}
