package comflower.sagongsa.auth;

import comflower.sagongsa.auth.exception.InvalidCredentialsException;
import comflower.sagongsa.auth.request.LoginRequest;
import comflower.sagongsa.auth.request.SignupRequest;
import comflower.sagongsa.common.exception.InvalidFormBodyException;
import comflower.sagongsa.user.User;
import comflower.sagongsa.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    @Transactional
    public User signup(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new InvalidFormBodyException(new HashMap<>() {{
                put("username", "이미 사용중인 아이디입니다.");
            }});
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword()) // TODO: Encrypt password
                .nickname(request.getNickname())
                .email(request.getEmail())
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(InvalidCredentialsException::new);

        // TODO: Encrypt password
        if (!Objects.equals(user.getPassword(), request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }
}
