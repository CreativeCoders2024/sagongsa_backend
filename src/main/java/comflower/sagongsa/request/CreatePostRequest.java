package comflower.sagongsa.request;

import comflower.sagongsa.util.TagHelper;
import lombok.Getter;
import org.springframework.validation.Errors;

@Getter
public class CreatePostRequest implements Request {
    private String title;
    private String content;
    private int memberCount;
    private int maxMemberCount;
    private int topic;
    private long endedAt;

    @Override
    public void validate(Errors errors) {
        if (title == null || title.isBlank()) {
            errors.rejectValue("title", "title.required", "제목을 입력해주세요.");
        }
        if (content == null || content.isBlank()) {
            errors.rejectValue("content", "content.required", "내용을 입력해주세요.");
        }
        if (memberCount < 1) {
            errors.rejectValue("memberCount", "memberCount", "참여 인원은 1명 이상이어야 합니다.");
        }
        if (maxMemberCount < memberCount) {
            errors.rejectValue("maxMemberCount", "maxMemberCount", "최대 참여 인원은 현재 참여 인원보다 많아야 합니다.");
        }
        if (!TagHelper.isTag(TagHelper.POST_TAGS, topic)) {
            errors.rejectValue("topic", "topic.invalid", "올바르지 않은 주제입니다.");
        }
    }
}
