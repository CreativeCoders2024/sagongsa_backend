package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.form.FormSignup;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.form.FormLogin;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(FormSignup formSignup) {
        validateDuplicateUser(formSignup);
        User signupUser = User.builder()
                .id(formSignup.getId())
                .pw(formSignup.getPw())
                .nickname(formSignup.getNickname())
                .email((formSignup.getEmail()))
                .build();
        userRepository.save(signupUser);
    }

    // 회원가입 - ID 중복 체크
    private void validateDuplicateUser(FormSignup formSignup) {
        userRepository.findById(formSignup.getId())
                .ifPresent(u -> {
                    throw new IllegalStateException("User with id : " + u.getId() + " already exists");
                    //여기서 아예 500 에러로 떠버리는데 어떻게 에러 메세지를 보내지?
                });
    }

    @Transactional
    public String login(FormLogin formLogin) {
        Optional<User> findUser = userRepository.findById(formLogin.getId());
        if (findUser.isPresent()) {
            if(findUser.get().getPw().equals(formLogin.getPw())) {
                return "Success Login : " + findUser.get().getUser_id();
            }
            else {
                return "Wrong Password";
            }
        }
        else {
            return "User not found : " + formLogin.getId();
        }
        // 이 리턴 처리를 service에서 하는게 맞나?
    }
}
