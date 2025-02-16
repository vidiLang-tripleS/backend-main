package TripleS.VidiLang.member.entity;

import TripleS.VidiLang.folder.entity.Folder;
import TripleS.VidiLang.global.common.entitiy.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MEMBER_EMAIL", unique = true, nullable = false)
    private String email;

    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String ImageUrl;

    private String socialId; // 로그인한 소셜 타입의 식별자 값

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Folder> folders;

    @Builder
    public Member(String email, String password, String nickName, String imageUrl, String socialId, SocialType socialType) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.ImageUrl = imageUrl;
        this.socialId = socialId;
        this.socialType = socialType;
    }
}
