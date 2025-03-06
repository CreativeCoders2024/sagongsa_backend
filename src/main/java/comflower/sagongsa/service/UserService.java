package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.InvalidCredentialsException;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.request.EditUserRequest;
import comflower.sagongsa.request.LoginRequest;
import comflower.sagongsa.request.SignupRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User createUser(SignupRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(request.getUsername());
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
    public User loginUser(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(InvalidCredentialsException::new);

        // TODO: Encrypt password
        if (!Objects.equals(user.getPassword(), request.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    @Transactional
    public User editUser(User user, EditUserRequest request) {
        if (request.getNickname() != null) {
            user.setNickname(request.getNickname());
        }
        if (request.getField() != null) {
            user.setField(request.getField());
        }
        if (request.getIntroduction() != null) {
            user.setIntroduction(request.getIntroduction());
        }

        return userRepository.save(user);  // 이거 안써주니까 수정이 안됨
    }

    @Transactional
    public void deleteUser(User withDrawUser) {
        withDrawUser.setWithdrawn(true);
        userRepository.save(withDrawUser); // 얘도
    }
}
