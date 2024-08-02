package comflower.sagongsa.controller;

import comflower.sagongsa.dto.EditUserDTO;
import comflower.sagongsa.dto.LoginDTO;
import comflower.sagongsa.dto.SignupDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.repository.UserRepository;
import comflower.sagongsa.response.ResponseDTO;
import comflower.sagongsa.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequiredArgsConstructor  //얘 찾아보기
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<ResponseDTO> signup(@RequestBody SignupDTO signupDTO) {
        Optional<User> existingId = userService.validateDuplicateUser(signupDTO.getId());
        if (existingId.isPresent()) {  // 아이디가 존재함 -> 중복 ID
            // 나중에는 로그로 바꿔보고 싶음 !
            System.out.println("이미 존재하는 아이디");
            return status(HttpStatus.CONFLICT).build();
        } else {
            userService.signup(signupDTO);
            System.out.println("회원가입 성공");
            // 에러 처리 부분..
            return status(HttpStatus.CREATED).build();
        }
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