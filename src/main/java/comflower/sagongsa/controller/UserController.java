package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.UserIdDTO;
import comflower.sagongsa.dto.response.*;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.*;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        if (userService.isUserPresentById(signupDTO.getId())) {
            throw new UserAlreadyExistsException(signupDTO.getId());
        }

        User createUser = userService.signup(signupDTO);
        SignupResponse body = SignupResponse.builder()
                .userId(createUser.getUserId())
                .token("JWT Token") // 이 부분은 로그인 후 실제 토큰으로 대체되어야 함
                .build();

        return ResponseEntity
                .created(URI.create(String.valueOf(createUser.getUserId())))
                .body(body);
    }

    // 로그인
    @PostMapping("/login")
    public SignupResponse login(@RequestBody LoginDTO loginDTO) {
        User findLoginUser = userRepository.findById(loginDTO.getUsername())
                .orElseThrow(() -> new InvalidIdException(loginDTO.getUsername()));

        if (!userService.login(loginDTO, findLoginUser)) {
            throw new InvalidPasswordException(loginDTO.getPassword());
        }

        // JWT 생성
        String token = "JWT Token"; // JWT 토큰 생성 로직 필요

        return SignupResponse.builder()
                .userId(findLoginUser.getUserId())
                .token(token) // 생성된 토큰 반환
                .build();
    }

    // 회원가입 에러 처리
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        return ErrorResponse.entity(ErrorType.USER_ALREADY_EXISTS, e.getId());
    }

    // 로그인 에러 처리
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPassword(InvalidPasswordException e) {
        return ErrorResponse.entity(ErrorType.WRONG_PASSWORD, e.getPassword());
    }

    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<ErrorResponse> handleWrongId(InvalidIdException e) {
        return ErrorResponse.entity(ErrorType.WRONG_ID, e.getId());
    }

    // 회원 정보 수정
    @PostMapping("/user/edit/info")
    public UserIdResponse editUser(@RequestBody EditUserDTO editUserDTO) {
        if (!userService.isUserPresentByUserId(editUserDTO.getUserId())) {
            throw new UserNotFoundException(editUserDTO.getUserId());
        }

        User findEditUser = userRepository.findByUserId(editUserDTO.getUserId()).get();
        userService.editUser(editUserDTO, findEditUser);

        return UserIdResponse.builder()
                .userId(editUserDTO.getUserId())
                .build();
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.entity(ErrorType.USER_NOT_FOUND, e.getUserId());
    }

    // 회원 정보 조회
    @PostMapping("/user/inquiry")
    public InquiryOfUserResponse inquiryOfUserInfo(@RequestBody UserIdDTO userIdDTO) {
        User userInfo = userService.inquiryOfUserInfo(userIdDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));

        return InquiryOfUserResponse.builder()
                .profile_img(userInfo.getProfileImg())
                .field(userInfo.getField())
                .introduction(userInfo.getIntroduction())
                .build();
    }

    // 회원 탈퇴
    @PutMapping("/withdraw")
    public UserIdResponse withDraw(@RequestBody UserIdDTO userIdDTO) {
        User withDrawUser = userRepository.findByUserId(userIdDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));
        userService.withDraw(withDrawUser);

        return UserIdResponse.builder()
                .userId(withDrawUser.getUserId())
                .build();
    }
}
