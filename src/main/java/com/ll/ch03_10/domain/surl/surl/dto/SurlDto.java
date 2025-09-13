package com.ll.ch03_10.domain.surl.surl.dto;

import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SurlDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String authorName;
    private long authorId;
    private String body;
    private String url;
    private long count;

    public SurlDto(Surl surl) {
        this.id = surl.getId();
        this.createDate = surl.getCreateDate();
        this.modifyDate = surl.getModifyDate();
        this.authorId = surl.getAuthor().getId();
        this.authorName = surl.getAuthor().getName();
        this.body = surl.getBody();
        this.url = surl.getUrl();
        this.count = surl.getCount();
    }
}
