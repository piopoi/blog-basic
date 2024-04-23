package com.piopoi.blogbasic.member.repository;

import com.piopoi.blogbasic.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
