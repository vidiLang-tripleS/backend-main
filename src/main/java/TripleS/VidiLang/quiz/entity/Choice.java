package TripleS.VidiLang.quiz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        indexes = {
                @Index(name = "idx_choice_question_id", columnList = "question_id"),
                @Index(name = "idx_choice_correct", columnList = "question_id, isCorrect")
        }
)
public class Choice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; // 선택지 내용 -> 해당 부분 의논&리팩토링 필요함

    @Column(nullable = false)
    private boolean isCorrect; // 정답 여부

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Builder
    public Choice(String text, boolean isCorrect, Question question) {
        this.text = text;
        this.isCorrect = isCorrect;
        this.question = question;
    }
}
