package com.piopoi.blogbasic.member.domain;

import java.util.ArrayList;
import java.util.List;

import com.piopoi.blogbasic.common.domain.BaseEntity;
import com.piopoi.blogbasic.member.dto.MemberCreateRequest;
import com.piopoi.blogbasic.post.domain.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@ToString(exclude = {"posts", "password"})
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

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

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
