package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.request.UserIdDTO;
import comflower.sagongsa.dto.response.*;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.InvalidIdException;
import comflower.sagongsa.error.InvalidPasswordException;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "User", description = "User API")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입 -> 여기 회원가입 로직 부분만 보면됨 자준!
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원가입 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "회원 중복",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        // 우선, 존재하는 ID인지 확인함
        if (userService.isUserPresentById(signupDTO.getId())) {
            // 위의 조건문이 true라면 = ID가 이미 존재함 = 중복 ID !! = 에러처리 해야함
            throw new UserAlreadyExistsException(signupDTO.getId());  // 에러처리를 해주는 함수 실행 (53번 줄) -> 지금 여기서 에러남 ㅠ
        }

        User createUser = userService.signup(signupDTO);

        SignupResponse body = SignupResponse.builder()
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
    @Operation(summary = "로그인", description = "로그인을 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SignupResponse.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 로그인 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "회원 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public SignupResponse login(@RequestBody LoginDTO loginDTO) {
        // 객체 반환 여부 판별 후 null 이면 바로 오류 처리 -> ID 판별
        User findLoginUser = userRepository.findById(loginDTO.getId())
                .orElseThrow(() -> new InvalidIdException(loginDTO.getId()));

        // PW 판별
        if (!userService.login(loginDTO, findLoginUser)) {
            throw new InvalidPasswordException(loginDTO.getPw());
        }

        return SignupResponse.builder()
                .userId(findLoginUser.getUserId())
                .token("JWT Token")
                .build();
    }

    // 로그인 에러처리
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
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserIdResponse.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원 정보 수정 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "회원 없음",
                      content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserIdResponse editUser(@RequestBody EditUserDTO editUserDTO) {
        User user = userService.findUserById(editUserDTO.getUserId());
        userService.editUser(editUserDTO, user);

        return UserIdResponse.builder()
                .userId(editUserDTO.getUserId())
                .build();
    }

    // 회원 정보 조회
    @PostMapping("/user/inquiry")
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = InquiryOfUserResponse.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원 정보 조회 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "회원 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public InquiryOfUserResponse inquiryOfUserInfo(@RequestBody UserIdDTO userIdDTO) {
        User user = userService.findUserById(userIdDTO.getUserId());
        return InquiryOfUserResponse.builder()
                .profile_img(user.getProfileImg())
                .field(user.getField())
                .introduction(user.getIntroduction())
                .build();
    }

    // 회원 탈퇴
    @PutMapping("/withdraw")
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 탈퇴 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserIdResponse.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원 탈퇴 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "회원 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserIdResponse withDraw(@RequestBody UserIdDTO userIdDTO) {
        User withDrawUser = userRepository.findByUserId(userIdDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));
        userService.withDraw(withDrawUser);

        return UserIdResponse.builder()
                .userId(withDrawUser.getUserId())
                .build();
    }
}
