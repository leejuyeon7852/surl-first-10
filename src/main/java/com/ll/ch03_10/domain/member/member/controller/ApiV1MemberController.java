package com.ll.ch03_10.domain.member.member.controller;

import com.ll.ch03_10.domain.auth.auth.service.AuthTokenService;
import com.ll.ch03_10.domain.member.member.dto.MemberDto;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.app.AppConfig;
import com.ll.ch03_10.global.exceptions.GlobalException;
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
    private final AuthTokenService authTokenService;
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

    @AllArgsConstructor
    @Getter
    public static class MemberLoginReqBody {
        @NotBlank
        private String username;
        @NotBlank
        private String password;
    }

    @AllArgsConstructor
    @Getter
    public static class MemberLoginRespBody {
        MemberDto item;
    }

    @PostMapping("/login")
    @Transactional
    public RsData<MemberLoginRespBody> login(
            @RequestBody @Valid MemberLoginReqBody reqBody
    ) {
        Member member = memberService
                .findByUsername(reqBody.username)
                .orElseThrow(() -> new GlobalException("401-1", "해당 회원을 찾을 수 없습니다"));

        if(!memberService.matchPassword(reqBody.password, member.getPassword())){
            throw new GlobalException("401-2", "비밀번호가 일치하지 않습니다");
        }

        String accessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());
        rq.setCookie("accessToken", accessToken);
        rq.setCookie("refreshToken", member.getRefreshToken());

        return RsData.of(
                "200-1",
                "로그인이 되었습니다.",
                new MemberLoginRespBody(
                        new MemberDto(member)
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
