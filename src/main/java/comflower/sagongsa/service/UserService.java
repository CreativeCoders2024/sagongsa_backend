package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.dto.EditUserDTO;
import comflower.sagongsa.dto.SignupDTO;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.dto.LoginDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 회원가입 - ID 중복 체크
    public Optional<User> validateDuplicateUser(SignupDTO signupDTO) {
        return userRepository.findById(signupDTO.getId());
    }

    @Transactional
    public User signup(SignupDTO signupDTO) {
        //validateDuplicateUser(signupDTO);
        User signupUser = User.builder()
                .id(signupDTO.getId())
                .pw(signupDTO.getPw())
                .nickname(signupDTO.getNickname())
                .email((signupDTO.getEmail()))
                .build();
        return userRepository.save(signupUser);
    }

    @Transactional
    public String login(LoginDTO loginDTO) {
        Optional<User> findUser = userRepository.findById(loginDTO.getId());
        if (findUser.isPresent()) {
            if(findUser.get().getPw().equals(loginDTO.getPw())) {
                return "Success Login : " + findUser.get().getUserId();
            }
            else {
                return "Wrong Password";
            }
        }
        else {
            return "User not found : " + loginDTO.getId();
        }
        // 이 리턴 처리를 service에서 하는게 맞나?
    }

    @Transactional
    public void editUser(EditUserDTO editUserDTO) {
        User editUser = userRepository.findByUserId(editUserDTO.getUserId()).orElseThrow(() ->
                new IllegalStateException("User with id : " + editUserDTO.getUserId() + " not found"));
        if (editUserDTO.getPw() != null) {
            editUser.setPw(editUserDTO.getPw());
        }
        if (editUserDTO.getNickname() != null) {
            editUser.setNickname(editUserDTO.getNickname());
        }
        //userRepository.save(editUser);
        // save를 굳이 안써도 영속성 컨텍스트가 자동으로 변경사항을 인지하여 데이터베이스에 반영
    }

    // 리턴이나 service 로직 부분 싹 다 내일 깔끔하게 수정!!
    @Transactional
    public void inquiryOfUserInfo(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        // return은 내일
    }

    @Transactional
    public void withDraw(Long userId) {
        User editUser = userRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalStateException("User with id : " + userId + " not found"));
        if (editUser.getUserId().equals(userId)) {
            editUser.setWithdrawn(true);
        }
    }
}
