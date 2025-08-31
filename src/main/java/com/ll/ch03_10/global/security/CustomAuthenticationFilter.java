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
        String actorUsername = rq.getCookieValue("actorUsername", null);
        String actorPassword = rq.getCookieValue("actorPassword", null);

        if(actorUsername == null || actorPassword == null){
            String authorization = req.getHeader("Authorization");
            if( authorization != null ){
                authorization = authorization.substring("bearer ".length());
                String[] authorizationBits = authorization.split(" ", 2);//띄어쓰기 기준으로
                actorUsername = authorizationBits[0];
                actorPassword = authorizationBits.length == 2 ? authorizationBits[1] : null;
            }
        }

        if(Ut.str.isBlank(actorUsername) || Ut.str.isBlank(actorPassword)){
            filterChain.doFilter(req, resp);
            return;
        }

        Member loginedMember = memberService.findByUsername(actorUsername).orElseThrow(null);
        if(loginedMember == null){
            filterChain.doFilter(req, resp);
            return;
        }


        if(!memberService.matchPassword(actorPassword, loginedMember.getPassword()))
            filterChain.doFilter(req, resp);

        //인증..?
        User user = new User(loginedMember.getUsername(), "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, resp); //필터 종료하고 다음 턴으로 넘긴다
    }
}