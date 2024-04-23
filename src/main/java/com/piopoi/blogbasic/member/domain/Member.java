package com.piopoi.blogbasic.member.domain;

import com.piopoi.blogbasic.common.domain.BaseEntity;
import com.piopoi.blogbasic.member.dto.MemberCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString
@EqualsAndHashCode(of = {"id"}, callSuper = false)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Email
    private String email;

    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    @Builder
    private Member(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static Member from(MemberCreateRequest memberCreateRequest) {
        return Member.builder()
                .email(memberCreateRequest.getEmail())
                .password(memberCreateRequest.getPassword())
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
