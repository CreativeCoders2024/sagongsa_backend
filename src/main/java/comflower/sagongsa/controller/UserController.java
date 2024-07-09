package comflower.sagongsa.controller;

import comflower.sagongsa.dto.EditUserDTO;
import comflower.sagongsa.dto.LoginDTO;
import comflower.sagongsa.dto.SignupDTO;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입 -> OK !!
    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO signupDTO) {
        userService.signup(signupDTO);
        return "Success Signup : " + signupDTO.getId() + " return";
    }


    // 로그인 -> OK...?
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        String return_code =  userService.login(loginDTO);
        return return_code;
    }

    // 회원 정보 수정
    @PutMapping("/user")
    public String editUser(@RequestBody EditUserDTO editUserDTO) {
        userService.editUser(editUserDTO);
        return "Success Edit User : " + editUserDTO.getUserId() + " return";
    }

}