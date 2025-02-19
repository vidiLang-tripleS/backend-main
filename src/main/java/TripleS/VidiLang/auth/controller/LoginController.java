package TripleS.VidiLang.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import TripleS.VidiLang.global.common.dto.ApiResponseTemplate;
import TripleS.VidiLang.global.exception.SuccessCode;
import TripleS.VidiLang.auth.dto.SignUpRequest;
import TripleS.VidiLang.auth.service.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {
	private final LoginService loginService;

	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponseTemplate<String>> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
		loginService.signUp(signUpRequest);
		return ApiResponseTemplate.success(SuccessCode.SIGNUP_USER_SUCCESS, "회원가입이 성공적으로 완료되었습니다.");

	}
}
