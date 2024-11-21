package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean isUserPresentById(String id) {
        return userRepository.findById(id).isPresent();
    }

    public User findUserByUserId(Long userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException(userId));
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

    @Transactional
    public User editUser(User user, EditUserDTO editUserDTO) {
        if (editUserDTO.getNickname() != null) {
            user.setNickname(editUserDTO.getNickname());
        }
        if (editUserDTO.getField() != null) {
            user.setField(editUserDTO.getField());
        }
        if (editUserDTO.getIntroduction() != null) {
            user.setIntroduction(editUserDTO.getIntroduction());
        }

        return userRepository.save(user);  // 이거 안써주니까 수정이 안됨
    }

    @Transactional
    public void withDraw(User withDrawUser) {
        withDrawUser.setWithdrawn(true);
        userRepository.save(withDrawUser); // 얘도
    }
}
