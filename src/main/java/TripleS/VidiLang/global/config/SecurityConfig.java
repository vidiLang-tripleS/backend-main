package TripleS.VidiLang.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import TripleS.VidiLang.jwt.JwtFilter;
import TripleS.VidiLang.jwt.JwtTokenProvider;
import TripleS.VidiLang.oauth2.handler.OAuth2LoginFailureHandler;
import TripleS.VidiLang.oauth2.handler.OAuth2LoginSuccessHandler;
import TripleS.VidiLang.oauth2.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
	private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedOriginPatterns(List.of("*"));
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE","PATCH", "HEAD", "OPTIONS"));
		corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		JwtFilter jwtFilter = new JwtFilter(jwtTokenProvider);
		http.authorizeHttpRequests(auth -> auth
			.requestMatchers("/", "/login", "/join", "/sign-up", "/test").permitAll()
			.anyRequest().authenticated()
		);

		http.oauth2Login(customConfigurer -> customConfigurer
			.successHandler(oAuth2LoginSuccessHandler)
			.failureHandler(oAuth2LoginFailureHandler)
			.userInfoEndpoint(endpointConfig -> endpointConfig.userService(customOAuth2UserService))
		);

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		http.formLogin(FormLoginConfigurer::disable);
		http.csrf(AbstractHttpConfigurer::disable);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}




}
