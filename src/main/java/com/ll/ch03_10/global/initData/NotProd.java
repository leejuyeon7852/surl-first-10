package com.ll.ch03_10.global.initData;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.domain.article.article.service.ArticleService;
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
import org.springframework.context.annotation.Profile;

@Profile("!prod") //!prod == dev or test일 때
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Lazy
    @Autowired
    private NotProd self; //외우기

    private final MemberService memberService;
    private final ArticleService articleService; //bean이 들어오게 된다?
    @Autowired
    private MemberRepository memberRepository;

    @Bean
    public ApplicationRunner initNotProd(){//spring boot와 약속된 class? 시작할때 처음으로 시작됨 바로
        return args -> {
            self.work1();
            self.work2(); //트랜잭션 먹힘

        }; //프로그램 실행될 때 자동으로 실행
    }

    @Transactional
    public void work1() {
        if(articleService.count() > 0) return; //읽기 트랜잭션 1

        Member member1 = memberService.join("user1", "1234", "유저1").getData();
        Member member2 = memberService.join("user2", "1234", "유저2").getData();

        Article article1 = articleService.write(member1,"제목1", "내용1").getData();
        Article article2 = articleService.write(member1, "제목2", "내용2").getData();

        Article article3 = articleService.write(member2,"제목1", "내용1").getData();
        Article article4 = articleService.write(member2, "제목2", "내용2").getData();

    }

    @Transactional
    public void work2() { //select

    }

}
