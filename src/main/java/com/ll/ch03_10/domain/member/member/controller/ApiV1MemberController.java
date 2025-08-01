package com.ll.ch03_10.domain.member.member.controller;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.global.rsData.RsData;
import com.ll.ch03_10.standard.dto.util.Ut;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class ApiV1MemberController {
    private final MemberService memberService;

    @AllArgsConstructor
    @Getter
    private static class MemberJoinBody {
        private String username;
        private String password;
        private String nickname;
    }

    @PostMapping("")
    public RsData<Member> join(
        @RequestBody MemberJoinBody reqBody
    ){
        if (Ut.str.isBlank(reqBody.username)){
            throw new GlobalException("400-1", "아이디를 입력해주세요");
        }
        if (Ut.str.isBlank(reqBody.password)){
            throw new GlobalException("400-1", "비밀번호를 입력해주세요");
        }
        if (Ut.str.isBlank(reqBody.nickname)){
            throw new GlobalException("400-1", "닉네임을 입력해주세요");
        }
        try{
            return memberService.join(reqBody.username, reqBody.password, reqBody.nickname);
        }
        catch(GlobalException e){
            if(e.getRsData().getResultCode().equals("400-1")){
                log.debug("이미 존재하는 아이디입니다.");
            }
            return RsData.of("400-A", "이미 존재하는 아이디입니다.", Member.builder().build());
        }


    }

}
