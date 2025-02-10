package TripleS.VidiLang.quiz;

public enum QuizStatus {

    INCOMPLETE("미완료"),
    INPROGRESS("진행중"),
    COMPLETE("완료");

    private final String status;

    QuizStatus(String status) {
        this.status = status;
    }
}
