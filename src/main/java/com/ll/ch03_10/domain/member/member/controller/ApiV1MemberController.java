package com.ll.ch03_10.domain.member.member.controller;

import com.ll.ch03_10.domain.member.member.dto.MemberDto;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.rq.Rq;
import com.ll.ch03_10.global.rsData.RsData;
import com.ll.ch03_10.standard.dto.Empty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiV1MemberController {
    private final MemberService memberService;
    private final Rq rq;

    @AllArgsConstructor
    @Getter
    public static class MemberJoinReqBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
        @NotBlank
        private String nickname;
    }

    @AllArgsConstructor
    @Getter
    public static class MemberJoinRespBody {
        MemberDto item;
    }

    @PostMapping("")
    @Transactional
    public RsData<MemberJoinRespBody> join(
            @RequestBody @Valid MemberJoinReqBody reqBody
    ) {
        RsData<Member> joinRs = memberService.join(reqBody.username, reqBody.password, reqBody.nickname);

        return joinRs.newDataOf(
                new MemberJoinRespBody(
                        new MemberDto(joinRs.getData())
                )
        );
    }

    @DeleteMapping("/logout")
    @Transactional
    public RsData<Empty> logout() {
        rq.removeCookie("actorUsername");
        rq.removeCookie("actorPassword");

        return RsData.OK;
    }
}
