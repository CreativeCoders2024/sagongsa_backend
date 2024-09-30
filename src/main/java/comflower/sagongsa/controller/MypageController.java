package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.EditUserFieldDTO;
import comflower.sagongsa.dto.request.EditUserIntroductionDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.UserFieldResponse;
import comflower.sagongsa.dto.response.UserIntroductionResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.MypageService;
import comflower.sagongsa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Tag(name = "user")
public class MypageController {
    private final MypageService mypageService;
    private final UserService userService;
    private final UserRepository userRepository;

    // 소개글 수정을 위해 불러오는 로직
    @GetMapping("/introduction")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "소개글 불러오기", description = "소개글을 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소개글 불러오기 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserIntroductionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserIntroductionResponse getIntroduction() {
        // Use user id from authentication
        long userId = 1L;
        User user = userService.findUserById(userId);

        return UserIntroductionResponse.builder()
                .introduction(user.getIntroduction())
                .build();
    }

    // 소개글 작성 및 수정
    @PutMapping("/introduction")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "소개글 수정", description = "소개글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "소개글 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserIntroductionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserIntroductionResponse setIntroduction(@RequestBody EditUserIntroductionDTO editIntroDTO) {
        // Use user id from authentication
        long userId = 1L;
        User user = userService.findUserById(userId);
        mypageService.setIntroduction(user, editIntroDTO.getIntroduction());

        return UserIntroductionResponse.builder()
                .introduction(user.getIntroduction())
                .build();
    }

    // 관리자 권한 수정
    @PutMapping("/manager")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "관리자 권한 수정", description = "관리자 권한을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "관리자 권한 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserIntroductionResponse.class))}),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserIntroductionResponse editUserManager() {
        // Use user id from authentication
        long userId = 1L;
        User user = userService.findUserById(userId);
        mypageService.editUserManager(user);

        return UserIntroductionResponse.builder()
                .introduction(user.getIntroduction())
                .build();
    }

    // 분야 불러오기
    @GetMapping("/field")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "분야 불러오기", description = "분야를 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "분야 불러오기 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFieldResponse.class))}),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserFieldResponse getField() {
        // Use user id from authentication
        long userId = 1L;
        User user = userService.findUserById(userId);

        return UserFieldResponse.builder()
                .field(user.getField())
                .build();
    }

    // 분야 수정
    @PutMapping("/field")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "분야 수정", description = "분야를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "분야 수정 성공",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFieldResponse.class))}),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    public UserFieldResponse setField(@RequestBody EditUserFieldDTO editFieldDTO) {
        // Use user id from authentication
        long userId = 1L;
        User findEditUserField = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        mypageService.setField(findEditUserField, editFieldDTO.getField());

        return UserFieldResponse.builder()
                .field(editFieldDTO.getField())
                .build();
    }
}
