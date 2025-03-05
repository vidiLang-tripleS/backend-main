package TripleS.VidiLang.member;

import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;

public class TestMember {
    public static Member createDefaultMember() {
        return Member.builder()
                .email("test@example.com")
                .password("encodedPassword123") // 비밀번호는 일반적으로 인코딩된 값 사용
                .nickName("testUser")
                .imageUrl("https://example.com/profile.jpg")
                .socialId("123456789")
                .socialType(SocialType.GOOGLE) // 테스트 기본값 설정
                .build();
    }

    public static Member createMemberWithEmail(String email) {
        return Member.builder()
                .email(email)
                .password("encodedPassword123")
                .nickName("testUser")
                .imageUrl("https://example.com/profile.jpg")
                .socialId("123456789")
                .socialType(SocialType.GOOGLE)
                .build();
    }

    public static Member createMemberWithSocialType(SocialType socialType) {
        return Member.builder()
                .email("test@example.com")
                .password("encodedPassword123")
                .nickName("testUser")
                .imageUrl("https://example.com/profile.jpg")
                .socialId("123456789")
                .socialType(socialType)
                .build();
    }
}
