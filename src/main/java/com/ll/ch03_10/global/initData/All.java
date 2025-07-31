package com.ll.ch03_10.global.initData;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.repository.MemberRepository;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
public class All {
    @Lazy
    @Autowired
    private All self; //외우기

    private final MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Bean
    @Order(3)
    public ApplicationRunner initAll(){//spring boot와 약속된 class? 시작할때 처음으로 시작됨 바로
        return args -> {
            self.work1();
        }; //프로그램 실행될 때 자동으로 실행
    }

    @Transactional
    public void work1() {
        if(memberService.count() > 0) return; //읽기 트랜잭션 1

        Member memberSystem = memberService.join("system", "1234", "시스템").getData();
        Member memberAdmin = memberService.join("admin", "1234", "관리자").getData();

        Member memberUser1 = memberService.join("user1", "1234", "유저1").getData();
        Member memberUser2 = memberService.join("user2", "1234", "유저2").getData();

    }

}
