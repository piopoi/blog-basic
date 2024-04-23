package com.piopoi.blogbasic.member.service;

import com.piopoi.blogbasic.member.domain.Member;
import com.piopoi.blogbasic.member.dto.MemberCreateRequest;
import com.piopoi.blogbasic.member.dto.MemberGetResponse;
import com.piopoi.blogbasic.member.dto.PasswordUpdateRequest;
import com.piopoi.blogbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(MemberCreateRequest memberCreateRequest) {
        Member member = Member.from(memberCreateRequest);
        return memberRepository.save(member)
                .getId();
    }

    public MemberGetResponse getMemberById(Long memberId) {
        Member member = findMemberById(memberId);
        return MemberGetResponse.from(member);
    }

    @Transactional
    public void updatePassword(Long memberId, PasswordUpdateRequest passwordUpdateRequest) {
        Member member = findMemberById(memberId);
        member.updatePassword(passwordUpdateRequest.getPassword());
    }

    @Transactional
    public void deleteMember(Long memberId) {
        findMemberById(memberId);
        memberRepository.deleteById(memberId);
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    }
}
