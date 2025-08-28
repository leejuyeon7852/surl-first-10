package com.ll.ch03_10.domain.surl.surl.repository;

import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SurlRepository extends JpaRepository<Surl, Long> {

}
