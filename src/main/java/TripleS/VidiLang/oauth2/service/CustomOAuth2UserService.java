package TripleS.VidiLang.oauth2.service;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;
import TripleS.VidiLang.member.repository.MemberRepository;
import TripleS.VidiLang.oauth2.OAuthAttributes;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;
	private static final String KAKAO = "kakao";

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		// DefaultOAuth2UserService 객체를 생성하여 OAuth2User 정보를 가져옴
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		// OAuth2 제공자의 이름 (예: kakao, naver, google)
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		SocialType socialType = getSocialType(registrationId);

		// UserInfo의 Json 값
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		// socialType에 따라 유저 정보를 통해 OAuthAttributes 객체 생성
		OAuthAttributes extractAttributes = OAuthAttributes.of(socialType, userNameAttributeName, attributes);

		// 소셜 로그인 유저를 가져오거나 저장
		Member createdMember = getMember(extractAttributes, socialType);

		// DefaultOAuth2User 반환
		return new DefaultOAuth2User(
			Collections.emptySet(),
			attributes,
			userNameAttributeName
		);
	}

	private SocialType getSocialType(String registrationId) {
		if (KAKAO.equals(registrationId)) {
			return SocialType.KAKAO;
		}
		return SocialType.GOOGLE;
	}

	private Member getMember(OAuthAttributes attributes, SocialType socialType) {
		// 이미 존재하는 유저를 조회
		Member findMember = memberRepository.findBySocialTypeAndSocialId(socialType, attributes.getOauth2UserInfo().getId()).orElse(null);

		// 유저가 없으면 저장
		if (findMember == null) {
			return saveMember(attributes, socialType);
		}
		return findMember;
	}

	private Member saveMember(OAuthAttributes attributes, SocialType socialType) {
		Member createdMember = attributes.toEntity(socialType, attributes.getOauth2UserInfo());
		return memberRepository.save(createdMember);
	}

}
