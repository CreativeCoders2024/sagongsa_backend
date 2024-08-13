package comflower.sagongsa.controller;


import comflower.sagongsa.dto.request.EditIntroductionDTO;
import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.dto.request.UserIdDTO;
import comflower.sagongsa.dto.response.ErrorResponse;
import comflower.sagongsa.dto.response.ErrorType;
import comflower.sagongsa.dto.response.MypageImportIntroductionResponse;
import comflower.sagongsa.dto.response.UserIdResponse;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.error.UserNotFoundException;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.service.MypageService;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
    private final UserService userService;
    private final UserRepository userRepository;

    // 소개글 수정을 위해 불러오는 로직
    @PostMapping("/import/intro")
    public MypageImportIntroductionResponse importIntroduction(@RequestBody UserIdDTO userIdDTO) {
        User importIntroductionUser = mypageService.importingIntroduction(userIdDTO)
                .orElseThrow(() -> new UserNotFoundException(userIdDTO.getUserId()));

        String introduction = importIntroductionUser.getIntroduction();
        if(introduction == null) {
            introduction = "null";
        }

        MypageImportIntroductionResponse importIntro = MypageImportIntroductionResponse
                .builder()
                .introduction(introduction)
                .build();
        return importIntro;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        return ErrorResponse.entity(ErrorType.USER_NOT_FOUND, e.getUserId());
    }


    // 소개글 작성 및 수정
    @PutMapping("/edit/intro")
    public UserIdResponse editUserIntroduction(@RequestBody EditIntroductionDTO editIntroDTO) {
        if(!userService.isUserPresentByUserId(editIntroDTO.getUserId())) {
            throw new UserNotFoundException(editIntroDTO.getUserId());
        }
        User findEditUserIntro = userRepository.findByUserId(editIntroDTO.getUserId()).get();
        mypageService.editUserIntroduction(findEditUserIntro, editIntroDTO);

        UserIdResponse editintroResponse = UserIdResponse
                .builder()
                .userId(editIntroDTO.getUserId())
                .build();

        return editintroResponse;
    }


    // 관리자 권한 수정
    @PutMapping("/manager")
    public void editUserManager(@RequestBody EditUserDTO editUserDTO) {

    }

    // 분야 불러오기
    @PostMapping("/import/field")
    public void importField(@RequestBody EditUserDTO editUserDTO) {

    }

    // 분야 수정
    @PutMapping("/edit/field")
    public void editField(@RequestBody EditUserDTO editUserDTO) {

    }
}
