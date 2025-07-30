package com.ll.ch03_10.domain.article.article.repository;

import com.ll.ch03_10.domain.article.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
