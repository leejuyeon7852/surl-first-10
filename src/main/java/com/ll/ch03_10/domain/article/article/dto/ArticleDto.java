package com.ll.ch03_10.domain.article.article.dto;

import com.ll.ch03_10.domain.article.article.entity.Article;
import com.ll.ch03_10.global.jpa.entity.BaseTime;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;


@NoArgsConstructor(access=PROTECTED)
public class ArticleDto extends BaseTime {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String authorName;
    private long authorId;
    private String title;
    private String body;

    public ArticleDto(Article article){
        this.id=article.getId();
        this.createDate=article.getCreateDate();
        this.modifyDate=article.getModifyDate();
        this.authorId=article.getAuthor().getId();
        this.authorName=article.getAuthor().getName();
        this.title=article.getTitle();
        this.body=article.getBody();
    }
}
