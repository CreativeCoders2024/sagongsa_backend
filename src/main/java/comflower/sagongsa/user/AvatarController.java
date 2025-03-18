package comflower.sagongsa.user;

import comflower.sagongsa.common.exception.UnknownUserException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/avatars")
@RequiredArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping("/@me")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<byte[]> getSelfAvatar(@AuthenticationPrincipal User user) {
        var avatar = user.getAvatar();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(avatar != null ? avatar.getData() : avatarService.getDefaultAvatar());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long userId) {
        if (userId == null) {
            throw new UnknownUserException();
        }

        var avatar = avatarService.getAvatar(userId);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(avatar != null ? avatar.getData() : avatarService.getDefaultAvatar());
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @SecurityRequirement(name = "bearerAuth")
    public void uploadAvatar(@AuthenticationPrincipal User user, @RequestPart("file") MultipartFile file) {
        avatarService.uploadAvatar(user, file);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteAvatar(@AuthenticationPrincipal User user) {
        avatarService.deleteAvatar(user);
    }
}
