package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 메서드
    public User signup(SignupDTO signupDTO) {
        User user = User.builder()
                .id(signupDTO.getId())
                .pw(passwordEncoder.encode(signupDTO.getPw()))  // 비밀번호 암호화하여 저장
                .nickname(signupDTO.getNickname())
                .email(signupDTO.getEmail())
                .build();
        return userRepository.save(user);
    }

    // 사용자 ID 존재 여부 확인
    public boolean isUserPresentById(String id) {
        return userRepository.findById(id).isPresent();
    }

    // 사용자 이름 기반 조회 메서드
    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username); // 필요에 따라 ID 혹은 username 필드명을 확인
    }

    // 비밀번호 검증 메서드
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
