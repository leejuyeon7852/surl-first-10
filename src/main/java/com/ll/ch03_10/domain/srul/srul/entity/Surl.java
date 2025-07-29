package com.ll.ch03_10.domain.srul.srul.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Surl {
    private long id;
    @Builder.Default
    private LocalDateTime creatDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modifyDate = LocalDateTime.now();
    private String body;
    private String url;
    @Setter(AccessLevel.NONE) //count만 setter를 막고 싶을 때
    private long count;

    public void increaseCount(){
        count++;
    }
}
