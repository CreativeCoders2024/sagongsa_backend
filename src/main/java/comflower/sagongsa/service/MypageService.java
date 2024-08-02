package comflower.sagongsa.service;


import comflower.sagongsa.dto.request.EditUserDTO;
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
    public Optional<User> importingIntroduction(EditUserDTO editUserDTO) {
        return userRepository.findById(editUserDTO.getUserId());
    }

    @Transactional
    public void editUserIntroduction(EditUserDTO editUserDTO) {

    }
}