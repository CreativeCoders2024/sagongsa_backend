package comflower.sagongsa.user;

import comflower.sagongsa.common.exception.UnknownUserException;
import comflower.sagongsa.user.request.EditUserRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(UnknownUserException::new);
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
