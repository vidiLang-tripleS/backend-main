package TripleS.VidiLang.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;
import TripleS.VidiLang.member.entity.SocialType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;

	private static final List<String> NO_CHECK_URLS = List.of("/login", "/sign-up", "/health-check", "/", "/test"); // filter 요청 필요없는 url

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String requestURI = request.getRequestURI();

		// 요청 URL이 인증 제외 리스트에 포함되어 있으면 필터를 건너뛰기
		if (NO_CHECK_URLS.contains(requestURI)) {
			filterChain.doFilter(request, response);
			return;
		}

		Optional<String> accessToken = jwtTokenProvider.extractAccessToken(request);
		Optional<String> refreshToken = jwtTokenProvider.extractRefreshToken(request);


		// ✅ AccessToken이 유효하면 인증 진행
		if (accessToken.isPresent() && jwtTokenProvider.isTokenValid(accessToken.get())) {
			Authentication auth = jwtTokenProvider.getAuthentication(accessToken.get());
			SecurityContextHolder.getContext().setAuthentication(auth);
			filterChain.doFilter(request, response);
			return;
		}

		// ✅ AccessToken이 만료되었지만 RefreshToken이 유효하면 AccessToken 재발급


		// ❌ 둘 다 없거나 유효하지 않다면 401 Unauthorized 응답
		throw new CustomException(ErrorCode.UNAUTHORIZED_EXCEPTION, "유효한 인증 토큰이 없습니다.");

	}

}
