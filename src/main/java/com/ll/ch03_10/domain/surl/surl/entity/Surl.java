package com.ll.ch03_10.domain.surl.surl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.global.jpa.entity.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access=PROTECTED)
@NoArgsConstructor(access=PROTECTED)
public class Surl extends BaseTime {
    @ManyToOne
    @JsonIgnore // 나중에 제거할 거임
    private Member author;
    private String body;
    private String url;
    @Setter(AccessLevel.NONE) //count만 setter를 막고 싶을 때
    private long count;

    public void increaseCount(){
        count++;
    }
}
