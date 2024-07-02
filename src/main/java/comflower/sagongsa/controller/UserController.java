package comflower.sagongsa.controller;

import comflower.sagongsa.form.FormLogin;
import comflower.sagongsa.form.FormSignup;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입 -> OK !!
    @PostMapping("/signup")
    public String signup(@RequestBody FormSignup formSignup) {
        userService.signup(formSignup);
        return "Success Signup : " + formSignup.getId() + " return";
    }

    // 로그인 -> 보류
    @PostMapping("/login")
    public String login(@RequestBody FormLogin formLogin) {
        String return_code =  userService.login(formLogin);
        return return_code;
    }

}