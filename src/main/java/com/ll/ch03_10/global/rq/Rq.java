package com.ll.ch03_10.global.rq;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.standard.dto.util.Ut;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    public Member getMember(){
        String actorUsername = req.getParameter("actorUsername");
        if(Ut.str.isBlank(actorUsername)) throw new GlobalException("401-1", "인증정보를 입력해주세요.");

        Member loginedMember = memberService.findByUsername(actorUsername)
                .orElseThrow(() -> new GlobalException("401-2", "인증정보가 올바르지 않습니다"));

        return loginedMember;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }
}