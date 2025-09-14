package com.ll.ch03_10.global.initData;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.repository.MemberRepository;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.global.app.AppConfig;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class All {
    private final MemberService memberService;
    @Lazy
    @Autowired
    private All self; //외우기
    @Autowired
    private MemberRepository memberRepository;

    @Bean
    @Order(3)
    public ApplicationRunner initAll() {//spring boot와 약속된 class? 시작할때 처음으로 시작됨 바로
        return args -> {
            self.work1();
        }; //프로그램 실행될 때 자동으로 실행
    }

    @Transactional
    public void work1() {

        log.debug("initAll started");
        if (memberService.count() > 0) return; //읽기 트랜잭션 1

        Member memberSystem = memberService.join("system", "1234", "시스템").getData();
        if(AppConfig.isNotProd())  memberSystem.setRefreshToken(memberSystem.getUsername());

        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();
        if(AppConfig.isNotProd()) memberAdmin.setRefreshToken(memberAdmin.getUsername());

        Member memberUser1 = memberService.join("user1", "1234", "유저1").getData();
        if(AppConfig.isNotProd()) memberUser1.setRefreshToken(memberUser1.getUsername());

        Member memberUser2 = memberService.join("user2", "1234", "유저2").getData();
        if(AppConfig.isNotProd()) memberUser2.setRefreshToken(memberUser2.getUsername());

    }

}
