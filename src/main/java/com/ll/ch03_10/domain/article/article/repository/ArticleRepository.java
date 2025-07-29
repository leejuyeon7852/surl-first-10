package com.ll.ch03_10.domain.article.article.repository;

import com.ll.ch03_10.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    //실제로 쓰이지 않음 JAP 학습용
    List<Article> findByIdInOrderByTitleDescIdAsc(List<Long> ids);

    //실제로 쓰이지 않음 JAP 학습용
    List<Article> findByTitleContaining(String title);

    //실제로 쓰이지 않음 JAP 학습용
    List<Article> findByTitleAndBody(String title, String body);
}
