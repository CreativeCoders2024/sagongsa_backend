package comflower.sagongsa.service;

import comflower.sagongsa.dto.request.EditIntroductionDTO;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.EditUserFieldDTO;
import comflower.sagongsa.dto.request.UserIdDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.MypageRepository;
import comflower.sagongsa.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {
    private final UserRepository userRepository;

    @Transactional
    public Optional<User> importingIntroduction(UserIdDTO userIdDTO) {
        return userRepository.findByUserId(userIdDTO.getUserId());
    }

    @Transactional
    public void editUserIntroduction(User user, EditIntroductionDTO editIntroDTO) {
        user.setIntroduction(editIntroDTO.getIntroduction());
        userRepository.save(user);
    }

    @Transactional
    public void editUserManager(User editManageUser) {
        editManageUser.setManager(true);
        userRepository.save(editManageUser);
    }

    @Transactional
    public Optional<User> importField(Long userId) {
        return userRepository.findByUserId(userId);
    }

    @Transactional
    public void editUserField(User editFieldUser, EditUserFieldDTO editFieldDTO) {
        editFieldUser.setField(editFieldDTO.getField());
        userRepository.save(editFieldUser);
    }
}