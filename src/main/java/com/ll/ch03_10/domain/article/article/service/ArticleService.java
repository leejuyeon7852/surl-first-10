package com.ll.ch03_10.domain.article.article.service;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.domain.article.article.repository.ArticleRepository;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service //@Component 둘다 같음?
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArticleService {
    private final ArticleRepository articleRepository;

    //@Transactional(readOnly = true) //읽기만 할 거 같다면 - select
    public long count() {
        return articleRepository.count();
    }

    //리턴
    //- 이번에 생성된 게시물 번호
    //- 게시물 생성에 대한 결과 메시지
    //- 결과 코드
    @Transactional //읽기 아니기 때문에 붙여줌
    public RsData<Article> write(Member author, String title, String body) {
        Article article = Article
                .builder()
                .title(title)
                .body(body)
                .author(author)
                .build();
        articleRepository.save(article);

        return RsData.of("%d번 게시물이 작성되었습니다.".formatted(article.getId()),article);
    }

    @Transactional
    public void delete(Article article) {
        articleRepository.delete(article);
    }


    public Optional<Article> findById(long id) {
        return articleRepository.findById(id);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }
}
