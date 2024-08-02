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

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
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
//    @PostMapping("/login")
//    public ResponseEntity<ResponseDTO> login(@RequestBody LoginDTO loginDTO) {
//         Optional<User> existingId = userService.validateDuplicateUser(loginDTO.getId());
//         if (existingId.isPresent()) {  // 아이디가 존재함 -> PW랑 맞춰보기
//             userService.login(loginDTO, existingId);
//
//         }
//    }

    // 회원 정보 수정
    @PostMapping("/user/edit/info")
    public ResponseEntity<String> editUser(EditUserDTO editUserDTO) {
        try {
            userService.editUser(editUserDTO);
        } catch (Exception e) {  // 예외처리
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