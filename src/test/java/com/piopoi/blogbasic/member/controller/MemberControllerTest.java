package com.piopoi.blogbasic.member.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.piopoi.blogbasic.member.domain.Member;
import com.piopoi.blogbasic.member.dto.MemberCreateRequest;
import com.piopoi.blogbasic.member.dto.PasswordUpdateRequest;
import com.piopoi.blogbasic.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    private final String requestUri = "/api/members";
    private final String email = "abc@test.com";
    private final String password = "123456";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    private Member testMember;
    private Long testMemberId;

    @BeforeEach
    void setUp() {
        testMember = memberRepository.save(Member.builder()
                .email(email)
                .password(password)
                .build());
        testMemberId = testMember.getId();
    }

    @Test
    @DisplayName("사용자을 생성할 수 있다.")
    void createMember() throws Exception {
        //given
        String testEmail = "test@test.com";
        MemberCreateRequest memberCreateRequest = MemberCreateRequest.builder()
                .email(testEmail)
                .password(password)
                .build();

        //when then
        mockMvc.perform(post(requestUri)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberCreateRequest))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("id로 사용자를 조회할 수 있다.")
    void findMemberById() throws Exception {
        //when then
        mockMvc.perform(get(requestUri + "/{memberId}", testMemberId)
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testMember.getId()))
                .andExpect(jsonPath("$.email").value(testMember.getEmail()));
    }

    @Test
    @DisplayName("사용자 비밀번호를 변경할 수 있다.")
    void updatePassword() throws Exception {
        //given
        PasswordUpdateRequest passwordUpdateRequest = PasswordUpdateRequest.builder()
                .password("newPassword")
                .build();

        //when then
        mockMvc.perform(patch(requestUri + "/{memberId}/password", testMemberId)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("id로 사용자를 삭제할 수 있다.")
    void deleteMember() throws Exception {
        //when then
        mockMvc.perform(delete(requestUri + "/{memberId}", testMemberId)
                        .accept(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
