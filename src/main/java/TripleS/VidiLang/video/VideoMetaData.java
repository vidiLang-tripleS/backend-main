package TripleS.VidiLang.video;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VideoMetaData {

    private String summary;
    private String transcription;
    private String memo;

    // 추후 자가 검증 로직이 필요할 경우 정적 팩터리 메서드 패턴으로 변환
    public VideoMetaData(String summary, String transcription, String memo) {
        this.summary = summary;
        this.transcription = transcription;
        this.memo = memo;
    }
}
