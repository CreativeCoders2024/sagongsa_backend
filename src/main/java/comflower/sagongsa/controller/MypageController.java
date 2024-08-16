package comflower.sagongsa.controller;

import comflower.sagongsa.dto.request.EditUserIntroductionDTO;
import comflower.sagongsa.dto.request.EditUserFieldDTO;
import comflower.sagongsa.dto.response.*;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
    private final UserRepository userRepository;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.entity(ErrorType.USER_NOT_FOUND, e.getUserId());
    }

    // 소개글 수정을 위해 불러오는 로직
    @GetMapping("/introduction")
    public UserIntroductionResponse getIntroduction() {
        // Use user id from authentication
        long userId = 1L;
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserIntroductionResponse.builder()
                .introduction(user.getIntroduction())
                .build();
    }

    // 소개글 작성 및 수정
    @PutMapping("/introduction")
    public void setIntroduction(@RequestBody EditUserIntroductionDTO editIntroDTO) {
        // Use user id from authentication
        long userId = 1L;
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        mypageService.setIntroduction(user, editIntroDTO.getIntroduction());
    }

    // 관리자 권한 수정
    @PutMapping("/manager")
    public void editUserManager() {
        // Use user id from authentication
        long userId = 1L;
        User findEditUserManager = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        mypageService.editUserManager(findEditUserManager);
    }

    // 분야 불러오기
    @GetMapping("/field")
    public UserFieldResponse getField() {
        // Use user id from authentication
        long userId = 1L;
        User findImportField = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return UserFieldResponse.builder()
                .field(findImportField.getField())
                .build();
    }

    // 분야 수정
    @PutMapping("/field")
    public void setField(@RequestBody EditUserFieldDTO editFieldDTO) {
        // Use user id from authentication
        long userId = 1L;
        User findEditUserField = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        mypageService.setField(findEditUserField, editFieldDTO.getField());
    }
}
