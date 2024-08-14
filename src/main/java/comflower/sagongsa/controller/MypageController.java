package comflower.sagongsa.controller;


import comflower.sagongsa.dto.request.EditUserDTO;
import comflower.sagongsa.entity.User;
import comflower.sagongsa.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/user")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 소개글 수정을 위해 불러오는 로직
    @PostMapping("/import/intro")
    public ResponseEntity<Optional<User>> importIntroduction(@RequestBody EditUserDTO editUserDTO) {
        Optional<User> importingIntroduction_data = mypageService.importingIntroduction(editUserDTO);
        return ResponseEntity.ok(importingIntroduction_data);
    }

    // 소개글 작성 및 수정
    @PutMapping("/edit/intro")
    public ResponseEntity<EditUserDTO> editUserIntroduction(@RequestBody EditUserDTO editUserDTO) {
        mypageService.editUserIntroduction(editUserDTO);
        return ResponseEntity.ok(editUserDTO);
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
