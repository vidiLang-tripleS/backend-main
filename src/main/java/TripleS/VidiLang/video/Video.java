package TripleS.VidiLang.video;

import TripleS.VidiLang.folder.Folder;
import TripleS.VidiLang.global.common.entitiy.BaseTimeEntity;
import TripleS.VidiLang.quiz.Quiz;
import TripleS.VidiLang.quiz.QuizStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String youtubeUrl;

    @Embedded
    private VideoMetaData videoMetaData;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private QuizStatus quizStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id", nullable = false)
    private Folder folder;

    @OneToMany(mappedBy = "video",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private List<Quiz> quizzes;

    // 개발 초기에 빠른 개발을 위해서 Builder패턴으로 수정 -> 추후 리팩토링 당시 정적 팩터리 메서드 패턴으로 수정
    @Builder
    public Video(String youtubeUrl, VideoMetaData videoMetaData) {
        this.youtubeUrl = youtubeUrl;
        this.videoMetaData = videoMetaData;
        this.quizStatus = QuizStatus.INCOMPLETE;
    }
}
