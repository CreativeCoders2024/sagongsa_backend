package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@RestControllerAdvice//모든 Controller에 대해 ExceptionHandler를 설정합니다.
public class UserController {
    private final UserService userService;

    // 회원가입 -> OK !!
    @PostMapping("/signup")
    public String signup(@RequestBody SignupDTO signupDTO) {
        userService.signup(signupDTO);
        return "Success Signup : " + signupDTO.getId() + " return";
    }

    // 로그인 -> OK...?
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        return userService.login(loginDTO);
    }

    // 회원 정보 수정
    @PutMapping("/user")
    public String editUser(@RequestBody EditUserDTO editUserDTO) {
        userService.editUser(editUserDTO);
        return "Success Edit User : " + editUserDTO.getUserId() + " return";
    }

    @PostMapping("/user")
    public String inquiryOfUserInfo(@RequestBody Long userId) {
        userService.inquiryOfUserInfo(userId);
        return "Success Inquiry Of User : " + userId + " return";
    }

    @PutMapping("/withdraw")
    public String withDraw(@RequestBody Long userId) {
        userService.withDraw(userId);
        return "Success Withdraw Of User : " + userId + " return";
    }
}
