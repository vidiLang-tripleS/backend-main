package TripleS.VidiLang.jwt;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;
import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;
import TripleS.VidiLang.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	private final RedisTemplate<String, String> redisTemplate;

	private final MemberRepository memberRepository;

	@Value("${spring.jwt.secret_key}")
	private String secretKey;

	@Value("${spring.jwt.access.expiration}")
	private int accessExpiration;

	@Value("${spring.jwt.refresh.expiration}")
	private int refreshExpiration;

	// Access 토큰 생성
	public String createAccessToken(String email, SocialType socialType) {
		Claims claims = Jwts.claims().setSubject(email);
		claims.put("socialType", socialType.name());

		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessExpiration))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	// Refresh 토큰 생성 & redis에 저장
	public String createRefreshToken(String email) {
		Date now = new Date();
		String refreshToken = Jwts.builder()
			.setSubject(email)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshExpiration))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();

		redisTemplate.opsForValue().set(email, refreshToken, refreshExpiration, TimeUnit.MILLISECONDS);
		return refreshToken;
	}

	// 토큰에서 이메일과 socialType을 가져와서 Authentication 객체 생성
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.parseClaimsJws(token)
			.getBody();
		String email = claims.getSubject();
		String socialTypeStr = claims.get("socialType", String.class);
		SocialType socialType = (socialTypeStr != null) ? SocialType.valueOf(socialTypeStr) : SocialType.LOCAL;

		// 사용자 조회
		Member member = memberRepository.findByEmailAndSocialType(email, socialType)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER_EXCEPTION, "해당 유저가 존재하지 않습니다."));

		// 소셜 로그인 사용자는 비밀번호가 없을 수 있으므로 임시 비밀번호 설정
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = (member.getPassword() != null) ? member.getPassword() : passwordEncoder.encode("defaultPassword");

		UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
			.username(member.getEmail())
			.password(password)
			.roles("USER")
			.build();

		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

	// 토큰 검증 (유효하지 않은 경우 예외 발생)
	public boolean isTokenValid(String token) {
		if (token == null || token.isEmpty()) {
			throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION, "요청에 유효한 토큰이 없습니다. 인증이 필요합니다.");
		}

		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true; // 검증 성공
		} catch (ExpiredJwtException e) {
			throw new CustomException(ErrorCode.EXPIRED_TOKEN_EXCEPTION, "JWT 토큰이 만료되었습니다.");
		} catch (JwtException e) {
			throw new CustomException(ErrorCode.INVALID_TOKEN_EXCEPTION, "유효하지 않은 JWT 토큰입니다.");
		}
	}

	// Refresh Token 추출 (Bearer 제거)
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("Refresh-Token"))
			.filter(token -> token.startsWith("Bearer "))
			.map(token -> token.substring(7));
	}

	// Access Token 추출 (Bearer 제거)
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader("Authorization"))
			.filter(token -> token.startsWith("Bearer "))
			.map(token -> token.substring(7));
	}




}
