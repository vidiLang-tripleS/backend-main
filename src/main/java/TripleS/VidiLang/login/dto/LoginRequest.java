package TripleS.VidiLang.login.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	@NotBlank(message = "email을 입력하세요.")
	private String loginId;

	@NotBlank(message = "비밀번호를 입력하세요.")
	private String password;
}
