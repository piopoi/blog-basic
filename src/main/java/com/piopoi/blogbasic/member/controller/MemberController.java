package com.piopoi.blogbasic.member.controller;

import com.piopoi.blogbasic.member.domain.Member;
import com.piopoi.blogbasic.member.dto.MemberCreateRequest;
import com.piopoi.blogbasic.member.dto.MemberGetResponse;
import com.piopoi.blogbasic.member.dto.PasswordUpdateRequest;
import com.piopoi.blogbasic.member.service.MemberService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody @Valid MemberCreateRequest memberCreateRequest) {
        Long memberId = memberService.createMember(memberCreateRequest);
        return ResponseEntity.created(URI.create("/api/members/" + memberId)).build();
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberGetResponse> getMemberById(@PathVariable Long memberId) {
        MemberGetResponse memberGetResponse = memberService.getMemberById(memberId);
        return ResponseEntity.ok(memberGetResponse);
    }

    @PatchMapping("/{memberId}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long memberId,
                                               @RequestBody @Valid PasswordUpdateRequest passwordUpdateRequest) {
        memberService.updatePassword(memberId, passwordUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }
}
