package comflower.sagongsa.controller;

import comflower.sagongsa.config.JwtTokenProvider;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.response.LoginResponse;
import comflower.sagongsa.dto.response.SignupResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        if (userService.isUserPresentById(signupDTO.getId())) {
            throw new UserAlreadyExistsException(signupDTO.getId());
        }

        User createUser = userService.signup(signupDTO);
        SignupResponse body = SignupResponse.builder()
                .userId(createUser.getUserId())
                .token("JWT Token")
                .build();

        return ResponseEntity
                .created(URI.create(String.valueOf(createUser.getUserId())))
                .body(body);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        User user = userService.findByUsername(loginDTO.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userService.verifyPassword(loginDTO.getPassword(), user.getPw())) {
            throw new RuntimeException("Invalid password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        String token = jwtTokenProvider.createToken(authentication);
        LoginResponse response = new LoginResponse(token);
        return ResponseEntity.ok(response);
    }
}
