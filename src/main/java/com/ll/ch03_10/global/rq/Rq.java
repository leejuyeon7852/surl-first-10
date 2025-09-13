package com.ll.ch03_10.global.rq;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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

    public Member getMember() {
        if (member != null) return member;

        long id = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());

        member = memberService.findById(id).get();

        return member;
    }

    public String getCurrentUrlPath() {
        return req.getRequestURI();
    }

    public String getCookieValue(String cookieName, String defaultValue) {
        if (req.getCookies() == null) return defaultValue;

        return Arrays.stream(req.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(defaultValue);
    }

    public void removeCookie(String name) {
        ResponseCookie cookie = ResponseCookie.from(name)
                .path("/")
                .maxAge(0)
                .domain(getSiteCookieDomain())
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

    public void setCookie(String name, String value) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .path("/")
                .maxAge(60 * 60 * 24 * 365 * 10)
                .domain(getSiteCookieDomain())
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .build();

        resp.addHeader("Set-Cookie", cookie.toString());
    }

    private String getSiteCookieDomain() {
        return "localhost";
    }
}