package TripleS.VidiLang.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SignUpRequest {
	@NotBlank(message = "email을 입력하세요.")
	private String email;

	@NotBlank(message = "비밀번호를 입력하세요.")
	private String password;

	@NotBlank(message = "닉네임을 입력하세요.")
	private String nickName;
}
