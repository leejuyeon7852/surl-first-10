package com.ll.ch03_10.global.security;

import com.ll.ch03_10.domain.auth.auth.service.AuthTokenService;
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
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final MemberService memberService;
    private final AuthTokenService authTokenService;
    private final Rq rq;

    @Override
    @SneakyThrows
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) {
        String accessToken = rq.getCookieValue("accessToken", null);

        if(accessToken == null){
            String authorization = req.getHeader("Authorization");
            if( authorization != null ){
                accessToken = authorization.substring("bearer ".length());
            }
        }

        if(Ut.str.isBlank(accessToken)){
            filterChain.doFilter(req, resp);
            return;
        }

        if(!authTokenService.validateToken(accessToken)){
            filterChain.doFilter(req, resp);
            return;
        }

        Map<String, Object> accessTokenData = authTokenService.getDataFrom(accessToken);
        long id = (int) accessTokenData.get("id");

        //인증..?
        User user = new User(id + "", "", List.of());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(req, resp); //필터 종료하고 다음 턴으로 넘긴다
    }
}