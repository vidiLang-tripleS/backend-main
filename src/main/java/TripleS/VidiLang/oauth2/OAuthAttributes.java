package TripleS.VidiLang.oauth2;

import java.util.Map;

import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;
import TripleS.VidiLang.oauth2.userInfo.GoogleOAuth2UserInfo;
import TripleS.VidiLang.oauth2.userInfo.KakaoOAuth2UserInfo;
import TripleS.VidiLang.oauth2.userInfo.OAuth2UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {
	private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
	private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

	@Builder
	private OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}


	public static OAuthAttributes of(SocialType socialType,
		String userNameAttributeName, Map<String, Object> attributes) {
		if (socialType == SocialType.KAKAO) {
			return ofKakao(userNameAttributeName, attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new KakaoOAuth2UserInfo(attributes))
			.build();
	}

	public static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.nameAttributeKey(userNameAttributeName)
			.oauth2UserInfo(new GoogleOAuth2UserInfo(attributes))
			.build();
	}

	public Member toEntity(SocialType socialType, OAuth2UserInfo oauth2UserInfo) {
		return Member.builder()
			.socialType(socialType)
			.socialId(oauth2UserInfo.getId())
			.email(oauth2UserInfo.getEmail())
			.nickName(oauth2UserInfo.getNickname())
			.imageUrl(oauth2UserInfo.getImageUrl())
			.build();
	}
}
