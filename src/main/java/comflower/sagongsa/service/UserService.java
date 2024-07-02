package comflower.sagongsa.service;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.form.FormSignup;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.form.FormLogin;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void signup(FormSignup formSignup) {
        User signupUser = User.builder()
                .id(formSignup.getId())
                .pw(formSignup.getPw())
                .nickname(formSignup.getNickname())
                .email((formSignup.getEmail()))
                .build();
        userRepository.save(signupUser);
    }

    @Transactional
    public void login(FormLogin formLogin) {

        User loginUser = User.builder()
                .id(formLogin.getId())
                .pw(formLogin.getPw())
                .build();
        userRepository.save(loginUser);  //save?
    }
}
