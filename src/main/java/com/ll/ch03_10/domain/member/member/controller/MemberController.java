package com.ll.ch03_10.domain.member.member.controller;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.global.rsData.RsData;
import com.ll.ch03_10.standard.dto.util.Ut;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    @ResponseBody
    public RsData<Member> join(
        String username, String password, String nickname
    ){
        if (Ut.str.isBlank(username)){
            throw new GlobalException("400-1", "아이디를 입력해주세요");
        }
        if (Ut.str.isBlank(password)){
            throw new GlobalException("400-1", "비밀번호를 입력해주세요");
        }
        if (Ut.str.isBlank(nickname)){
            throw new GlobalException("400-1", "닉네임을 입력해주세요");
        }
        try{
            return memberService.join(username, password, nickname);
        }
        catch(GlobalException e){
            if(e.getRsData().getResultCode().equals("400-1")){
                log.debug("이미 존재하는 아이디입니다.");
            }
            return RsData.of("400-A", "커스텀 에러 메시지", Member.builder().build());
        }


    }
}
