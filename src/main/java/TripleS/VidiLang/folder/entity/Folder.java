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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
