package com.ll.ch03_10.global.initData;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.domain.article.article.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Profile("!prod") //!prod == dev or test일 때
@Configuration
@RequiredArgsConstructor
public class NotProd {
    @Lazy
    @Autowired
    private NotProd self; //외우기

    private final ArticleService articleService; //bean이 들어오게 된다?

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

        Article article1 = articleService.write("제목1", "내용1").getData(); //쓰기 트랜잭션 3
        Article article2 = articleService.write("제목2", "내용2").getData();

        article2.setTitle("제목!!!");

        articleService.delete(article1);
    }

    @Transactional
    public void work2() { //select
        //List : 0~ N
        //Optional : 0~ 1개
//        Optional<Article> opArticle1 =articleRepository.findById(2L);
//
//        opArticle1.get();

        Article article = articleService.findById(2L).get();

        List<Article> articles = articleService.findAll();


    }

}
