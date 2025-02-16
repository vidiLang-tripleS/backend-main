package TripleS.VidiLang.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import TripleS.VidiLang.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
