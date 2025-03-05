package TripleS.VidiLang.folder.entity;

import TripleS.VidiLang.global.common.entitiy.BaseTimeEntity;
import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.video.entity.Video;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Table(name = "folders")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Folder extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String name;

    @OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
    private List<Video> video = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ColorType colorType;

    @Enumerated(EnumType.STRING)
    private LanguageType languageType;

    @Builder
    public Folder(Member member, String name, ColorType colorType, LanguageType languageType) {
        validate(member, name, colorType, languageType);
        this.member = member;
        this.name = name;
        this.colorType = colorType;
        this.languageType = languageType;
    }

    private void validate(Member member, String name, ColorType colorType, LanguageType languageType) {
        Assert.notNull(member, "폴더 소유자는 null일 수 없습니다.");
        Assert.hasText(name, "폴더 이름은 필수입니다.");
        Assert.notNull(colorType, "색상 타입은 필수입니다.");
        Assert.notNull(languageType, "언어 타입은 필수입니다.");
    }

    public void update(String name, ColorType colorType, LanguageType languageType) {
        validate(this.member, name, colorType, languageType);
        updateField(this.name, name, () -> this.name = name, "폴더 이름은 필수입니다.");
        updateField(this.colorType, colorType, () -> this.colorType = colorType, "색상 타입은 필수입니다.");
        updateField(this.languageType, languageType, () -> this.languageType = languageType, "언어 타입은 필수입니다.");
    }

    private <T> void updateField(T currentValue, T newValue, Runnable updateAction, String errorMessage) {
        Assert.notNull(newValue, errorMessage);
        if (newValue instanceof String stringValue) {
            Assert.hasText(stringValue, errorMessage);
        }
        if (newValue instanceof Enum<?> enumValue) {
            Assert.notNull(enumValue, errorMessage);
        }
        if(!currentValue.equals(newValue)) {
            updateAction.run();
        }
    }
}
