package TripleS.VidiLang.oauth2.handler;

import java.io.IOException;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;
import TripleS.VidiLang.jwt.JwtTokenProvider;
import TripleS.VidiLang.member.entity.SocialType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException {
		// 이메일과 SocialType 가져오기
		String email = extractEmail(authentication);
		SocialType socialType = extractSocialType(authentication);

		// 액세스 토큰 & 리프레시 토큰 발급
		String accessToken = jwtTokenProvider.createAccessToken(email, socialType);
		String refreshToken = jwtTokenProvider.createRefreshToken(email);

		// 로그 출력
		log.info("✅ Access Token: {}", accessToken);
		log.info("✅ Refresh Token: {}", refreshToken);
		log.info("✅ SocialType: {}", socialType);

		// 로그인 성공 후 단순히 "/"로 리다이렉트
		response.sendRedirect("/test");
	}

	private String extractEmail(Authentication authentication) {
		DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

		// email 찾기 (Google, Naver 등)
		String email = (String) oAuth2User.getAttributes().get("email");

		// Kakao의 경우 kakao_account 내부에 email이 있음
		if (email == null) {
			Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");
			if (kakaoAccount != null) {
				email = (String) kakaoAccount.get("email");
			}
		}

		// 이메일이 여전히 null이면 예외 발생
		if (email == null) {
			throw new CustomException(ErrorCode.VALIDATION_REQUEST_FAIL_USERINFO_EXCEPTION, "소셜 로그인 사용자의 이메일을 찾을 수 없습니다.");
		}

		return email;
	}


	private SocialType extractSocialType(Authentication authentication) {
		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
			String provider = oauthToken.getAuthorizedClientRegistrationId(); // OAuth2 제공자 ID 가져오기

			if ("kakao".equalsIgnoreCase(provider)) return SocialType.KAKAO;
			if ("google".equalsIgnoreCase(provider)) return SocialType.GOOGLE;
		}

		return SocialType.LOCAL; // 기본값 (예외적인 경우)
	}
}
