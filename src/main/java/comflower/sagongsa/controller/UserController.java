package comflower.sagongsa.controller;

import comflower.sagongsa.dto.EditUserDTO;
import comflower.sagongsa.dto.LoginDTO;
import comflower.sagongsa.dto.SignupDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.response.ResponseDTO;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseDTO signup(@RequestBody SignupDTO signupDTO) {

        // 중복 체크
        Optional<User> validateDuplicateSignUp = userService.validateDuplicateUser(signupDTO);

        // 중복이 있다면 -> 에러 코드 리턴
        if (validateDuplicateSignUp.isPresent()) {

            return ResponseDTO.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Validation failed for ID")
                    .data(validateDuplicateSignUp.get().getId())
                    .build();
        }
        // 중복이 없다면 -> 회원가입 로직 실행
        else {
            User SignUpResponse = userService.signup(signupDTO);

            // 실행 후 리턴
            return ResponseDTO.builder()
                    .status(HttpStatus.CREATED)
                    .message("Sign up successful")
                    .data(SignUpResponse)
                    .build();
        }
    }





    // 로그인
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