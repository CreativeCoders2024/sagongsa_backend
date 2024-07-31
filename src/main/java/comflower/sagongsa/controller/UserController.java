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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
//    @PostMapping("/signup")
//    public ResponseDTO signup(@RequestBody SignupDTO signupDTO) {
//        // 중복 체크
//        Optional<User> validateDuplicateSignUp = userService.validateDuplicateUser(signupDTO);
//
//        // 중복이 있다면 -> 에러 코드 리턴
//        if (validateDuplicateSignUp.isPresent()) {
//
//            return ResponseDTO.builder()
//                    .status(HttpStatus.BAD_REQUEST)
//                    .message("Validation failed for ID")
//                    .data(validateDuplicateSignUp.get().getId())
//                    .build();
//        }
//        // 중복이 없다면 -> 회원가입 로직 실행
//        else {
//            User SignUpResponse = userService.signup(signupDTO);
//
//            // 실행 후 리턴
//            return ResponseDTO.builder()
//                    .status(HttpStatus.CREATED)
//                    .message("Sign up successful")
//                    .data(SignUpResponse)
//                    .build();
//        }
//    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody SignupDTO signupDTO) {
        Optional<User> existingId = userService.validateDuplicateUser(signupDTO);
        if (existingId.isPresent()) {
            return status(HttpStatus.CONFLICT).build();
        }
        return null;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        String return_code = userService.login(loginDTO);
        return return_code;
    }

    // 회원 정보 수정
    @PostMapping("/user/edit/info")
    public ResponseEntity<String> editUser(EditUserDTO editUserDTO) {
        try {
            userService.editUser(editUserDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success Edit User : " + editUserDTO.getUserId() + " return", HttpStatus.OK);
    }

    // 회원 정보 조회
    @PostMapping("/user/inquiry")
    public String inquiryOfUserInfo(@RequestBody Long userId) {
        userService.inquiryOfUserInfo(userId);
        return "Success Inquiry Of User : " + userId + " return";
    }

    // 회원 탈퇴
    @PutMapping("/withdraw")
    public String withDraw(@RequestBody Long userId) {
        userService.withDraw(userId);
        return "Success Withdraw Of User : " + userId + " return";
    }
}