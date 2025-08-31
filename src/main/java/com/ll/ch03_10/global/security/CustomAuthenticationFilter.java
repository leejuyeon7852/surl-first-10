package com.ll.ch03_10.global.security;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.rq.Rq;
import com.ll.ch03_10.standard.util.Ut;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {
        String apiKey = rq.getCookieValue("apiKey", null);

        if(apiKey == null){
            String authorization = req.getHeader("Authorization");
            if( authorization != null ){
                apiKey = authorization.substring("bearer ".length());
            }
        }

        if(Ut.str.isBlank(apiKey)){
            filterChain.doFilter(req, resp);
            return;
        }

        Member loginedMember = memberService.findByApiKey(apiKey).orElseThrow(null);
        if(loginedMember == null){
            filterChain.doFilter(req, resp);
            return;
        }

        //인증..?
        User user = new User(loginedMember.getId()+"", "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, resp); //필터 종료하고 다음 턴으로 넘긴다
    }
}