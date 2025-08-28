package com.ll.ch03_10.domain.surl.surl.service;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import com.ll.ch03_10.domain.surl.surl.repository.SurlRepository;
import com.ll.ch03_10.global.rsData.RsData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurlService {
    private final SurlRepository surlRepository;

    public List<Surl> findAll() {
        return surlRepository.findAll();
    }

    @Transactional
    public RsData<Surl> add(Member author, String body, String url) {
        Surl surl = Surl
                .builder()
                .body(body)
                .url(url)
                .author(author)
                .build();

        surlRepository.save(surl);

        return RsData.of("%d번 URL이 생성되었습니다.".formatted(surl.getId()),surl);
    }

    public Optional<Surl> findById(long id) {
        return surlRepository.findById(id);
    }

    @Transactional
    public void increaseCount(Surl surl) {
        surl.increaseCount();
    }

    @Transactional
    public void delete(Surl surl) {
        surlRepository.delete(surl);
    }
}
