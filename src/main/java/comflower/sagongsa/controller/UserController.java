package comflower.sagongsa.controller;

import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.SignupResponse;
import comflower.sagongsa.entity.User;
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

    // 회원가입 -> 여기 회원가입 로직 부분만 보면됨 자준!
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        // 우선, 존재하는 ID인지 확인함
        if (userService.isUserPresentById(signupDTO.getId())) {
            System.out.println("여기로 들어옴?\n");
            System.out.println(userService.isUserPresentById((signupDTO.getId())));
            // 위의 조건문이 true라면 = ID가 이미 존재함 = 중복 ID !! = 에러처리 해야함
            throw new UserAlreadyExistsException(signupDTO.getId());  // 에러처리를 해주는 함수 실행 (53번 줄) -> 지금 여기서 에러남 ㅠ
        }

        // 실제 회원가입 로직 실행 !!
        User createUser = userService.signup(signupDTO);

        // 성공했을 때 반환할 response를 만들어주는 부분
        long userId = 1L;
        SignupResponse body = SignupResponse
                .builder()
                .userId(createUser.getUserId())
                .token("JWT Token")
                .build();
        return ResponseEntity
                .created(URI.create(String.valueOf(userId)))
                .body(body);
    }

    // 여기 부분이 중복 ID 에러 처리하는 부분 !!
    @ExceptionHandler(UserAlreadyExistsException.class)  // error/UserAlreadyExistsException.java 파일 같이 보기
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        // ErrorType에 있는 USER_ALREADY_EXISTS 를 토대로 반환값이 나옴
        // code : 10000,
        // message : User already exists    요런식으로 !
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