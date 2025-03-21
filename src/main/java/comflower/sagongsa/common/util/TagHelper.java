package comflower.sagongsa.common.util;

import java.util.ArrayList;
import java.util.List;

public class TagHelper {
    public static final List<String> CONTEST_TAGS = List.of("해커톤", "아이디어톤", "디자인");
    public static final List<String> POST_TAGS = List.of(
            "프론트엔드", "백엔드", "데이터베이스", "데이터 과학", "디자인",
            "보안", "네트워크", "Android", "iOS", "AI", "Unity"
    );

    public static int encode(List<String> tags, List<String> selected) {
        int bitflag = 0;
        for (String tag : selected) {
            int index = tags.indexOf(tag);
            if (index >= 0) {
                bitflag |= (1 << index);
            }
        }
        return bitflag;
    }

    public static List<String> decode(List<String> tags, int bitflag) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            if ((bitflag & (1 << i)) != 0) {
                result.add(tags.get(i));
            }
        }
        return result;
    }

    public static boolean isTag(List<String> tags, int bitflag) {
        return decode(tags, bitflag).size() == 1;
    }
}
