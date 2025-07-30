package com.ll.ch03_10.domain.member.member.entity;

import com.ll.ch03_10.global.jpa.entity.BaseTime;
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
    private String username;
    private String password;
    private String nickname;
}
