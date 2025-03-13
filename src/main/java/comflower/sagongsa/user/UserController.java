package comflower.sagongsa.user;

import comflower.sagongsa.user.request.EditUserRequest;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor //얘 찾아보기
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

    @GetMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public User getUserSelf(@AuthenticationPrincipal User user) {
        return user;
    }

    @PutMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public User editUser(@AuthenticationPrincipal User user, @RequestBody EditUserRequest request) {
        return userService.editUser(user, request);
    }

    @DeleteMapping("/users/@me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
    }
}
