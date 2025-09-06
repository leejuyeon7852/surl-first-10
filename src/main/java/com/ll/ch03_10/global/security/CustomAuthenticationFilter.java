package com.ll.ch03_10.global.security;

import com.ll.ch03_10.domain.auth.auth.service.AuthTokenService;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.app.AppConfig;
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
        String refreshToken = rq.getCookieValue("refreshToken", null);

        if(accessToken == null || refreshToken == null){
            String authorization = req.getHeader("Authorization");
            if( authorization != null ){
                String authorizationBits[] = authorization.substring("bearer ".length()).split(" ", 2);
                if(authorizationBits.length==2) {
                    accessToken = authorizationBits[0];
                    refreshToken = authorizationBits[1];
                }
            }
        }

        if(Ut.str.isBlank(accessToken) || Ut.str.isBlank(refreshToken)){
            filterChain.doFilter(req, resp);
            return;
        }

        if(!authTokenService.validateToken(accessToken)){
            Member member = memberService.findByRefreshToken(refreshToken).orElse(null);
            if(member == null){
                filterChain.doFilter(req, resp);
                return;
            }
            String newAccessToken = authTokenService.genToken(member, AppConfig.getAccessTokenExpirationSec());
            rq.setCookie("accessToken", newAccessToken);

            accessToken = newAccessToken;
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