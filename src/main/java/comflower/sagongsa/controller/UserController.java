package comflower.sagongsa.controller;

import comflower.sagongsa.Placeholder;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.LoginDTO;
import comflower.sagongsa.dto.request.SignupDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.dto.response.SignupResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.InvalidCredentialsException;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "새로운 사용자 계정을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원가입 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "409", description = "회원 중복",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupDTO signupDTO) {
        if (userService.isUserPresentByUsername(signupDTO.getUsername())) {
            throw new UserAlreadyExistsException(signupDTO.getUsername());
        }

        User createdUser = userService.signup(signupDTO);
        SignupResponse body = SignupResponse.builder()
                .userId(createdUser.getId())
                .token(Placeholder.JWT_TOKEN)
                .build();
        return ResponseEntity
                .created(URI.create(createdUser.getId().toString()))  // header - Location에 추가해줌
                .body(body);
    }

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
        User user = userRepository.findByUsername(loginDTO.getId())
                .orElseThrow(InvalidCredentialsException::new);

        // TODO: Encrypt password
        if (Objects.equals(user.getPassword(), loginDTO.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return SignupResponse.builder()
                .userId(user.getId())
                .token(Placeholder.JWT_TOKEN)
                .build();
    }

    @GetMapping("/users/{userId}")
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "404", description = "회원 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public User getUser(@PathVariable Long userId) {
        return userService.findUserByUserId(userId);
    }

    @PutMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "회원 정보 수정", description = "회원 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
            @ApiResponse(responseCode = "400", description = "잘못된 회원 정보 수정 데이터",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public User editUser(@RequestBody EditUserDTO editUserDTO) {
        User user = userService.findUserByUserId(Placeholder.SELF_USER_ID);
        return userService.editUser(user, editUserDTO);
    }

    @DeleteMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
    })
    public void withDraw() {
        User user = userService.findUserByUserId(Placeholder.SELF_USER_ID);
        userService.withDraw(user);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(UserAlreadyExistsException e) {
        return ErrorResponse.entity(ErrorType.USER_ALREADY_EXISTS, e.getUsername());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials() {
        return ErrorResponse.entity(ErrorType.INVALID_CREDENTIALS);
    }
}
