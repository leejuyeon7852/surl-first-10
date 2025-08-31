package com.ll.ch03_10.global.rq;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Arrays;

@Component
@RequestScope
@RequiredArgsConstructor
public class Rq {
    private final HttpServletRequest req;
    private final HttpServletResponse resp;
    private final MemberService memberService;
    private Member member;

    public Member getMember(){
        if (member != null) return member;

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

        member = memberService.findByUsername(name).get();

        return member;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public String getCookieValue(String cookieName, String defaultValue) {
        if(req.getCookies() == null) return defaultValue;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(defaultValue);
    }

    public void removeCookie(String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); //수명
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public void setCookie(String actorUsername, String username) {
        Cookie cookie = new Cookie(actorUsername, username);
        cookie.setMaxAge(60 * 60 * 24 * 365); //수명
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}