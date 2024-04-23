package com.piopoi.blogbasic.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@Builder
public class MemberCreateRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
