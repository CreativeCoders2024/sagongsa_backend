package comflower.sagongsa.controller;

import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.ErrorType;
import comflower.sagongsa.error.InvalidCredentialsException;
import comflower.sagongsa.error.UserAlreadyExistsException;
import comflower.sagongsa.jwt.JwtHelper;
import comflower.sagongsa.request.EditUserRequest;
import comflower.sagongsa.request.LoginRequest;
import comflower.sagongsa.request.SignupRequest;
import comflower.sagongsa.response.ErrorResponse;
import comflower.sagongsa.response.SignupResponse;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "user")
public class UserController {
    private final UserService userService;
    private final JwtHelper jwtHelper;

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
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest request) {
        User user = userService.createUser(request);
        String jwt = jwtHelper.generateToken(user.getId());

        return ResponseEntity
                .created(URI.create(String.valueOf(user.getId())))  // header - Location에 추가해줌
                .body(
                        SignupResponse.builder()
                                .userId(user.getId())
                                .token(jwt)
                                .build()
                );
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
    public SignupResponse login(@RequestBody LoginRequest request) {
        User user = userService.loginUser(request);
        String jwt = jwtHelper.generateToken(user.getId());

        return SignupResponse.builder()
                .userId(user.getId())
                .token(jwt)
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
        return userService.findUserById(userId);
    }

    @GetMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "회원 정보 조회", description = "회원 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 정보 조회 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = User.class))}),
    })
    public User getUserSelf(@AuthenticationPrincipal User user) {
        return user;
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
    public User editUser(@AuthenticationPrincipal User user, @RequestBody EditUserRequest request) {
        return userService.editUser(user, request);
    }

    @DeleteMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Operation(summary = "회원 탈퇴", description = "회원을 탈퇴합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
    })
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
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
