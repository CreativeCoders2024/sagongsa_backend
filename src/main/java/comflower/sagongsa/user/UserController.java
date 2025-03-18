package comflower.sagongsa.user;

import comflower.sagongsa.user.request.EditUserRequest;
import comflower.sagongsa.user.response.UserResponse;
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
    public UserResponse getUser(@PathVariable Long userId) {
        var user = userService.findUserById(userId);
        return new UserResponse(user);
    }

    @GetMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public UserResponse getUserSelf(@AuthenticationPrincipal User user) {
        return new UserResponse(user);
    }

    @PutMapping("/users/@me")
    @SecurityRequirement(name = "bearerAuth")
    public UserResponse editUser(@AuthenticationPrincipal User user, @RequestBody EditUserRequest request) {
        var editedUser = userService.editUser(user, request);
        return new UserResponse(editedUser);
    }

    @DeleteMapping("/users/@me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @SecurityRequirement(name = "bearerAuth")
    public void deleteUser(@AuthenticationPrincipal User user) {
        userService.deleteUser(user);
    }
}
