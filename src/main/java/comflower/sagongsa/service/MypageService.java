package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;

    @Transactional
    public void setIntroduction(User user, String introduction) {
        user.setIntroduction(introduction);
        userRepository.save(user);
    }

    @Transactional
    public void setField(User user, int field) {
        user.setField(field);
        userRepository.save(user);
    }

    @Transactional
    public void editUserManager(User editManageUser) {
        editManageUser.setManager(true);
        userRepository.save(editManageUser);
    }
}
