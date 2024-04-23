package com.piopoi.blogbasic.member.dto;

import com.piopoi.blogbasic.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class MemberGetResponse {

    private Long id;
    private String email;

    public static MemberGetResponse from(Member member) {
        return MemberGetResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}
