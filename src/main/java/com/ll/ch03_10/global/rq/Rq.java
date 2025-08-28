package com.ll.ch03_10.global.rq;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;

    @Getter
    @Setter
    private Member member;

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }
}