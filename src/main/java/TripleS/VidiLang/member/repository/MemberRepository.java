package TripleS.VidiLang.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.entity.SocialType;

public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	Optional<Member> findByNickname(String nickname);

	Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

	Optional<Member> findByEmailAndSocialType(String email, SocialType socialType);

}
