package comflower.sagongsa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;

    @NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자를 생성해주는 친구
    @AllArgsConstructor  // 모든 필드를 매개변수로 받는 생성자를 생성해주는 친구
    @Builder  // 빌더 패턴을 적용
    @Getter
    @Entity  // JPA 엔티티
    @Table(name = "contest")  // 데이터베이스의 contest 테이블과 매핑됨을 나타내는 어노테이션
    public class Contest {

        @Id //기본키로 설정 자동으로 생성
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long contestId;
        //nullable이 false면 반드시 값을 가져야 함.
        //nullable이 true면  값을 가질 수도 있고 아닐 수도 있음

        @Column(nullable = false)
        private Long userId;

        @Column(nullable = false)
        private String title;

        @Column(nullable = true)
        private String img;

        @Column(nullable = false)
        private String prize;

        @Column(nullable = false)
        private LocalDateTime startedAt;

        @Column(nullable = false)
        private LocalDateTime endedAt;

        @Column(nullable = true)
        private String link;

        @Column(nullable = false)
        @ColumnDefault("0")
        private Long field;

        public void update(String title, String img, String prize, LocalDateTime startedAt, LocalDateTime endedAt, String link, Long field) {
            this.title = title;
            this.img = img;
            this.prize = prize;
            this.startedAt = startedAt;
            this.endedAt = endedAt;
            this.link = link;
            this.field = field;

        }


}
