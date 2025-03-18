package comflower.sagongsa.user;

import comflower.sagongsa.common.exception.UnknownUserException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final UserRepository userRepository;
    @Getter private final byte[] defaultAvatar;

    public AvatarService(AvatarRepository avatarRepository, UserRepository userRepository) {
        this.avatarRepository = avatarRepository;
        this.userRepository = userRepository;

        try {
            InputStream inputStream = new ClassPathResource("default-avatar.jpg").getInputStream();
            this.defaultAvatar = inputStream.readAllBytes();
        } catch (IOException e) {
            log.error("Failed to load default avatar", e);
            throw new RuntimeException("Failed to load default avatar", e);
        }
    }

    public Avatar getAvatar(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UnknownUserException();
        }

        return avatarRepository.findByUserId(userId);
    }

    public void uploadAvatar(User user, MultipartFile file) {
        Avatar avatar = user.getAvatar();
        if (avatar == null) {
            avatar = Avatar.builder().user(user).build();
        }

        try {
            avatar.setData(file.getBytes());
        } catch (IOException e) {
            log.error("Failed to upload avatar", e);
            throw new RuntimeException("Failed to upload avatar", e);
        }
        avatarRepository.save(avatar);
    }

    public void deleteAvatar(User user) {
        Avatar avatar = user.getAvatar();
        if (avatar != null) {
            avatarRepository.delete(avatar);
        }
    }
}
