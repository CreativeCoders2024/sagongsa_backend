package comflower.sagongsa.controller;


import comflower.sagongsa.dto.request.EditIntroductionDTO;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.EditUserFieldDTO;
import comflower.sagongsa.dto.request.UserIdDTO;
import comflower.sagongsa.dto.response.*;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.MypageService;
import comflower.sagongsa.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.entity(ErrorType.USER_NOT_FOUND, e.getUserId());
    }

    // 소개글 수정을 위해 불러오는 로직
    @PostMapping("/import/intro")
    public MypageImportIntroductionResponse importIntroduction(@RequestBody UserIdDTO userIdDTO) {
        User importIntroductionUser = mypageService.importingIntroduction(userIdDTO)
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));

        String introduction = importIntroductionUser.getIntroduction();
        if(introduction == null) {
            introduction = "null";
        }

        return MypageImportIntroductionResponse.builder()
                .introduction(introduction)
                .build();
    }

    // 소개글 작성 및 수정
    @PutMapping("/edit/intro")
    public UserIdResponse editUserIntroduction(@RequestBody EditIntroductionDTO editIntroDTO) {
        if(!userService.isUserPresentByUserId(editIntroDTO.getUserId())) {
            throw new UserNotFoundException(editIntroDTO.getUserId());
        }
        User findEditUserIntro = userRepository.findByUserId(editIntroDTO.getUserId()).get();
        mypageService.editUserIntroduction(findEditUserIntro, editIntroDTO);

        return UserIdResponse.builder()
                .userId(editIntroDTO.getUserId())
                .build();
    }

    // 관리자 권한 수정
    @PutMapping("/edit/manager")
    public UserIdResponse editUserManager(@RequestBody UserIdDTO editManagerDTO) {
        User findEditUserManager = userRepository.findByUserId(editManagerDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(editManagerDTO.getUserId()));
        mypageService.editUserManager(findEditUserManager);

        return UserIdResponse.builder()
                .userId(findEditUserManager.getUserId())
                .build();
    }

    // 분야 불러오기
    @PostMapping("/import/field")
    public ImportFieldResponse importField(@RequestBody UserIdDTO userIdDTO) {
        User findImportField = mypageService.importField(userIdDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));

        return ImportFieldResponse.builder()
                .field(findImportField.getField())
                .build();
    }

    // 분야 수정
    @PutMapping("/edit/field")
    public UserIdResponse editField(@RequestBody EditUserFieldDTO editFieldDTO) {
        User findEditUserField = userRepository.findByUserId(editFieldDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(editFieldDTO.getUserId()));
        mypageService.editUserField(findEditUserField, editFieldDTO);

        return UserIdResponse.builder()
                .userId(editFieldDTO.getUserId())
                .build();
    }
}
