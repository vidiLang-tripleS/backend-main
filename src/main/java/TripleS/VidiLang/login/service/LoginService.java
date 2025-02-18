package TripleS.VidiLang.login.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;
import TripleS.VidiLang.login.dto.SignUpRequest;
import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;
import TripleS.VidiLang.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Transactional
	public void signUp(SignUpRequest signUpRequest) {
		// ✅ 이미 존재하는 이메일 예외 처리
		if (memberRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_SUBJECT_EXCEPTION, "이미 존재하는 이메일입니다.");
		}

		// ✅ 이미 존재하는 닉네임 예외 처리
		if (memberRepository.findByNickName(signUpRequest.getNickName()).isPresent()) {
			throw new CustomException(ErrorCode.ALREADY_EXIST_SUBJECT_EXCEPTION, "이미 존재하는 닉네임입니다.");
		}

		// ✅ 회원 생성 및 저장
		Member member = Member.builder()
			.email(signUpRequest.getEmail())
			.nickName(signUpRequest.getNickName())
			.password(signUpRequest.getPassword())
			.socialType(SocialType.LOCAL)
			.build();

		member.updatePassword(passwordEncoder.encode(signUpRequest.getPassword()));

		memberRepository.save(member);
	}
}
