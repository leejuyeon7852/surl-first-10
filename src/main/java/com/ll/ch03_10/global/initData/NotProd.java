package com.ll.ch03_10.global.initData;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.domain.article.article.service.ArticleService;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.repository.MemberRepository;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import com.ll.ch03_10.domain.surl.surl.service.SurlService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Profile("!prod") //!prod == dev or test일 때 운영모드에서 실행 안됨
@Configuration
@RequiredArgsConstructor
public class NotProd {
    private final MemberService memberService;
    private final ArticleService articleService; //bean이 들어오게 된다?
    private final SurlService surlService;
    @Lazy
    @Autowired
    private NotProd self; //외우기
    @Autowired
    private MemberRepository memberRepository;


    //GitHub Action test
    @Bean
    @Order(4)
    public ApplicationRunner initNotProd() {//spring boot와 약속된 class? 시작할때 처음으로 시작됨 바로
        return args -> {
            self.work1();
        }; //프로그램 실행될 때 자동으로 실행
    }

    @Transactional
    public void work1() {
        if (articleService.count() > 0) return; //읽기 트랜잭션 1

        Member memberUser1 = memberService.findByUsername("user1").get();
        Member memberUser2 = memberService.findByUsername("user2").get();

        Article article1 = articleService.write(memberUser1, "제목1", "내용1").getData();
        Article article2 = articleService.write(memberUser1, "제목2", "내용2").getData();

        Article article3 = articleService.write(memberUser2, "제목3", "내용3").getData();
        Article article4 = articleService.write(memberUser2, "제목4", "내용4").getData();

        Surl surl1 = surlService.add(memberUser1, "네이버", "https://www.naver.com").getData();
        Surl surl2 = surlService.add(memberUser1, "다음", "https://www.daum.net").getData();

        Surl surl3 = surlService.add(memberUser2, "구글", "https://www.google.com").getData();
        Surl surl4 = surlService.add(memberUser2, "네이버", "https://www.naver.com").getData();

    }

}
