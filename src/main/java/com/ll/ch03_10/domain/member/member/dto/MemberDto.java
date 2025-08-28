package com.ll.ch03_10.domain.member.member.dto;

import com.ll.ch03_10.domain.member.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberDto {
    private long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String username;
    private String nickname;

    public MemberDto(Member member){
        this.id=member.getId();
        this.createDate=member.getCreateDate();
        this.modifyDate=member.getModifyDate();
        this.username=member.getUsername();
        this.nickname=member.getNickname();
    }
}
