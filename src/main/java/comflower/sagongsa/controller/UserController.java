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
import org.apache.coyote.Response;
import org.hibernate.query.sql.internal.ResultSetMappingProcessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

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

        User createUser = userService.signup(signupDTO);

        SignupResponse body = SignupResponse
                .builder()
                .userId(createUser.getUserId())
                .token("JWT Token")
                .build();
        return ResponseEntity
                .created(URI.create(String.valueOf(createUser.getUserId())))  // header - Location에 추가해줌
                .body(body);
    }

    // 회원가입 에러처리
    @ExceptionHandler(UserAlreadyExistsException.class)  // error/UserAlreadyExistsException.java 파일 같이 보기
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        // ErrorType에 있는 USER_ALREADY_EXISTS 를 토대로 반환값이 나옴
        // code : 10000,
        // message : User already exists    요런식으로 !
        return ErrorResponse.entity(ErrorType.USER_ALREADY_EXISTS, e.getId());
    }


    // 로그인
    @PostMapping("/login")
    public SignupResponse login(@RequestBody LoginDTO loginDTO) {
        // 객체 반환 여부 판별 후 null 이면 바로 오류 처리 -> ID 판별
        User findLoginUser = userRepository.findById(loginDTO.getId())
                .orElseThrow(() -> new WrongIdException(loginDTO.getId()));

        // PW 판별
        if(!userService.login(loginDTO, findLoginUser)) {
            throw new WrongPasswordException(loginDTO.getPw());
        }

        SignupResponse loginResponse = SignupResponse
                .builder()
                .userId(findLoginUser.getUserId())
                .token("JWT Token")
                .build();
        return loginResponse;
    }

    // 로그인 에러처리
    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<ErrorResponse> handleWrongPassword(WrongPasswordException e) {
        return ErrorResponse.entity(ErrorType.WRONG_PASSWORD, e.getPassword());
    }
    @ExceptionHandler(WrongIdException.class)
    public ResponseEntity<ErrorResponse> handleWrongId(WrongIdException e) {
        return ErrorResponse.entity(ErrorType.WRONG_ID, e.getId());
    }


    // 회원 정보 수정
    @PostMapping("/user/edit/info")
    public UserIdResponse editUser(@RequestBody EditUserDTO editUserDTO) {
        if(!userService.isUserPresentByUserId(editUserDTO.getUserId())) {
            throw new UserNotFoundException(editUserDTO.getUserId());
        }
        User findEditUser = userRepository.findByUserId(editUserDTO.getUserId()).get();
        userService.editUser(editUserDTO, findEditUser);

        UserIdResponse editInfoResponse = UserIdResponse
                .builder()
                .userId(editUserDTO.getUserId())
                .build();
        return editInfoResponse;
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

        InquiryOfUserResponse inquiryResponse = InquiryOfUserResponse
                .builder()
                .profile_img(userInfo.getProfileImg())
                .field(userInfo.getField())
                .introduction(userInfo.getIntroduction())
                .build();
        return inquiryResponse;
    }


    // 회원 탈퇴
    @PutMapping("/withdraw")
    public UserIdResponse withDraw(@RequestBody UserIdDTO userIdDTO) {
        if(!userService.isUserPresentByUserId(userIdDTO.getUserId())) {
            throw new UserNotFoundException(userIdDTO.getUserId());
        }
        User withDrawUser = userRepository.findByUserId(userIdDTO.getUserId()).get();
        if(!withDrawUser.getUserId().equals(userIdDTO.getUserId())) {  //굳?이 두개나?
            throw new UserNotFoundException(userIdDTO.getUserId());
        }
        userService.withDraw(withDrawUser);

        UserIdResponse withDrawResponse = UserIdResponse
                .builder()
                .userId(withDrawUser.getUserId())
                .build();
        return withDrawResponse;
    }
}