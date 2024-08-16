package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.dto.request.LoginDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public User signup(SignupDTO signupDTO) {
        User signupUser = User.builder()
                .id(signupDTO.getId())
                .pw(signupDTO.getPw())
                .nickname(signupDTO.getNickname())
                .email((signupDTO.getEmail()))
                .build();
        return userRepository.save(signupUser);
    }

    public boolean isUserPresentById(String id) {
        return userRepository.findById(id).isPresent();
        // ID가 있으면 true를 반환함
    }

    @Transactional
    public boolean login(LoginDTO loginDTO, User user) {
        return user.getPw().equals(loginDTO.getPw());
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
