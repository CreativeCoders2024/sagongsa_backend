package comflower.sagongsa.controller;

import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.SignupResponse;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController("/user")
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
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        if (userService.isUserPresentById(signupDTO.getId())) {
            throw new UserAlreadyExistsException(signupDTO.getId());
        }

        // TODO: 실제로 사용자 생성 ..

        long userId = 1L;
        SignupResponse body = SignupResponse
                .builder()
                .userId(userId)
                .token("JWT Token")
                .build();

        return ResponseEntity
                .created(URI.create(String.valueOf(userId)))
                .body(body);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        return ErrorResponse.entity(ErrorType.USER_ALREADY_EXISTS, e.getId());
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        String return_code = userService.login(loginDTO);
        return return_code;
    }

    // 회원 정보 수정
    @PostMapping("/edit")
    public ResponseEntity<String> editUser(EditUserDTO editUserDTO) {
        try {
            userService.editUser(editUserDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Success Edit User : " + editUserDTO.getUserId() + " return", HttpStatus.OK);
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