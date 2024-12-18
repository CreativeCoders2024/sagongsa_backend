package comflower.sagongsa.service;

import comflower.sagongsa.config.JwtTokenProvider;
import comflower.sagongsa.dto.JwtToken;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.error.InvalidIdException;
import comflower.sagongsa.error.InvalidPasswordException;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.dto.request.LoginDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User signup(SignupDTO signupDTO) {
        String encodedPassword = passwordEncoder.encode(signupDTO.getPw());
        User signupUser = User.builder()
                .id(signupDTO.getId())
                .pw(encodedPassword)
                .nickname(signupDTO.getNickname())
                .email((signupDTO.getEmail()))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        return userRepository.save(signupUser);
    }

    public boolean isUserPresentById(String id) {
        return userRepository.getById(id).isPresent();
        // ID가 있으면 true를 반환함
    }

    @Transactional
    public JwtToken login(LoginDTO loginDTO) {
        User user = userRepository.getById(loginDTO.getId())
                .orElseThrow(() -> new InvalidIdException(loginDTO.getId()));

        if (!passwordEncoder.matches(loginDTO.getPw(), user.getPw())) {
            throw new InvalidPasswordException(loginDTO.getPw());
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getNickname(), null, user.getAuthorities());
        return jwtTokenProvider.generateToken(authentication);

    }

    @Transactional
    public void editUser(EditUserDTO editUserDTO, User editUser) {
        if (editUserDTO.getPw() != null) {
            editUser.setPw(editUserDTO.getPw());
        }
        if (editUserDTO.getNickname() != null) {
            editUser.setNickname(editUserDTO.getNickname());
        }
        userRepository.save(editUser);  // 이거 안써주니까 수정이 안됨
    }

    @Transactional
    public void withDraw(User withDrawUser) {
        withDrawUser.setWithdrawn(true);
        userRepository.save(withDrawUser); // 얘도
    }
}
