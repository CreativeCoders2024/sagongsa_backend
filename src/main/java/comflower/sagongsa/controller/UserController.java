package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.SignupResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidCredentialsException;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.jwt.JwtHelper;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtHelper jwtHelper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        if (userService.isUserPresentByUsername(signupDTO.getUsername())) {
            throw new UserAlreadyExistsException(signupDTO.getUsername());
        }

        User createdUser = userService.signup(signupDTO);

        String jwtToken = jwtHelper.generateToken(createdUser.getId());
        SignupResponse body = SignupResponse.builder()
                .userId(createdUser.getId())
                .token(jwtToken)
                .build();

        return ResponseEntity
                .created(URI.create(createdUser.getId().toString()))  // header - Location에 추가해줌
                .body(body);
    }

    @PostMapping("/login")
    public SignupResponse login(@RequestBody LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(InvalidCredentialsException::new);

        // TODO: Encrypt password
        if (!Objects.equals(user.getPassword(), loginDTO.getPassword())) {
            throw new InvalidCredentialsException();
        }

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
    public User editUser(@AuthenticationPrincipal User user, @RequestBody EditUserDTO editUserDTO) {
        return userService.editUser(user, editUserDTO);
    }

    @DeleteMapping("/users/@me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        return ErrorResponse.entity(ErrorType.USER_ALREADY_EXISTS, e.getUsername());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials() {
        return ErrorResponse.entity(ErrorType.INVALID_CREDENTIALS);
    }
}
