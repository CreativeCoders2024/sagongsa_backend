package comflower.sagongsa.service;


import comflower.sagongsa.dto.request.EditIntroductionDTO;
import comflower.sagongsa.dto.request.EditUserDTO;
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

    private final MypageRepository mypageRepository;
    private final UserRepository userRepository;

    @Transactional
    public Optional<User> importingIntroduction(UserIdDTO userIdDTO) {
        return userRepository.findByUserId(userIdDTO.getUserId());
    }

    @Transactional
    public void editUserIntroduction(User user, EditIntroductionDTO editIntroDTO) {
        if(editIntroDTO.getIntroduction() != null) {
            user.setIntroduction(editIntroDTO.getIntroduction());
        }
        userRepository.save(user);
    }

    @Transactional
    public void editUserManager(User manageUser) {
        manageUser.setManager(true);
        userRepository.save(manageUser);
    }

    @Transactional
    public Optional<User> importField(Long userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        return user;
    }
}