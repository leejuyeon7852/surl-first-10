package com.ll.ch03_10.domain.surl.surl.controller;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import com.ll.ch03_10.domain.surl.surl.service.SurlService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.global.rq.Rq;
import com.ll.ch03_10.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SurlController {
    private final Rq rq;
    private final SurlService surlService;

    @GetMapping("/all")
    @ResponseBody
    public List<Surl> getAll(){
        return surlService.findAll();
    }

    @GetMapping("/add")
    @ResponseBody
    public RsData<Surl> add(String body, String url){
        Member member = rq.getMember(); //현재 브라우저로 로그인한 회원
        return surlService.add(member, body, url);
    }

    @GetMapping("/s/{body}/**") //**은 모든 것이 다 온다는 뜻
    @ResponseBody
    public RsData<Surl> add(
            @PathVariable String body,
            HttpServletRequest req
    ){
        //String requestURI = req.getRequestURI();

        String url = req.getRequestURI();

        if(req.getQueryString()!=null){
            url += "?" + req.getQueryString();
        }

        String[] urlBits = url.split("/", 4);// "/" 기준으로 나누고, 4개 이상 나누지 마라

        url = urlBits[3];

        Member member = rq.getMember();

        return surlService.add(member, body, url);
    }

    @GetMapping("/g/{id}")
    //@ResponseBody
    public String go(@PathVariable long id){

        //Surl surl = surlService.findById(id).get();
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        surlService.increaseCount(surl);
        return "redirect:"+ surl.getUrl();
    }
}
