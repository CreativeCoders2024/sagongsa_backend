package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.dto.request.LoginDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User signup(SignupDTO signupDTO) {
        User signupUser = User.builder()
                .id(signupDTO.getId())
                .pw(signupDTO.getPw())
                .nickname(signupDTO.getNickname())
                .email((signupDTO.getEmail()))
                .build();
        User createUser = userRepository.save(signupUser);
        return createUser;
    }

    public boolean isUserPresentById(String id) {
        return userRepository.findById(id).isPresent();
        // ID가 있으면 true를 반환함
    }

    @Transactional
    public boolean login(LoginDTO loginDTO, User user) {
        if(user.getPw().equals(loginDTO.getPw())) {
            return true;
        }
        else
            return false;
    }

    public boolean isUserPresentByUserId(Long userId) {
        return userRepository.findByUserId(userId).isPresent();
    }

    @Transactional
    public void editUser(EditUserDTO editUserDTO, User editUser) {
        if (editUserDTO.getPw() != null) {
            editUser.setPw(editUserDTO.getPw());
        }
        if (editUserDTO.getNickname() != null) {
            editUser.setNickname(editUserDTO.getNickname());
        }
    }

    @Transactional
    public Optional<User> inquiryOfUserInfo(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        return findUser;
    }

    @Transactional
    public void withDraw(User withDrawUser) {
        withDrawUser.setWithdrawn(true);
    }
}
