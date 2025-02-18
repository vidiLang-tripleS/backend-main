package TripleS.VidiLang.login.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import TripleS.VidiLang.jwt.JwtTokenProvider;
import TripleS.VidiLang.member.entity.SocialType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException {

		String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
		SocialType socialType = SocialType.LOCAL;// 인증 정보에서 Username(email) 추출

		String accessToken = jwtTokenProvider.createAccessToken(email, socialType); // AccessToken 발급
		String refreshToken = jwtTokenProvider.createRefreshToken(email); // RefreshToken 발급

		// 응답 헤더에 토큰 추가
		response.setHeader("Authorization", "Bearer " + accessToken);
		response.setHeader("Refresh-Token", refreshToken);

	}

	private String extractUsername(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return userDetails.getUsername();
	}
}
