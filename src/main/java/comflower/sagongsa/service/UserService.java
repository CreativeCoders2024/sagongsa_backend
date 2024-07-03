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

    @Transactional
    public void signup(SignupDTO signupDTO) {
        validateDuplicateUser(signupDTO);
        User signupUser = User.builder()
                .id(signupDTO.getId())
                .pw(signupDTO.getPw())
                .nickname(signupDTO.getNickname())
                .email((signupDTO.getEmail()))
                .build();
        userRepository.save(signupUser);
    }

    // 회원가입 - ID 중복 체크
    private void validateDuplicateUser(SignupDTO signupDTO) {
        userRepository.findById(signupDTO.getId())
                .ifPresent(u -> {
                    throw new IllegalStateException("User with id : " + u.getId() + " already exists");
                    //여기서 아예 500 에러로 떠버리는데 어떻게 에러 메세지를 보내지?
                });
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
}
