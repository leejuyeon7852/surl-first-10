package com.ll.ch03_10.domain.member.member.entity;

import com.ll.ch03_10.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access=PROTECTED)
@NoArgsConstructor(access=PROTECTED)
public class Member extends BaseTime {
    @Column(unique=true)
    private String username;
    private String password;
    private String nickname;
    @Column(unique=true)
    private String apiKey;

    public String getName(){
        return this.nickname;
    }
}
