package com.ll.ch03_10.domain.article.article.service;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.domain.article.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //@Component 둘다 같음?
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;


    public long count() {
        return articleRepository.count();
    }

    public Article write(String title, String body) {
        Article article = Article
                .builder()
                .title(title)
                .body(body)
                .build();
        return articleRepository.save(article);
    }

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
