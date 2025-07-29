package com.ll.ch03_10.domain.surl.surl.controller;

import com.ll.ch03_10.domain.srul.srul.entity.Surl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SurlController {
    private List<Surl> surls = new ArrayList<>();
    private long surlsLastId;

    @GetMapping("/all")
    @ResponseBody
    public List<Surl> getAll(){
        return surls;
    }

    @GetMapping("/add")
    @ResponseBody
    public Surl add(String body, String url){
        Surl surl = Surl
                .builder()
                .id(++surlsLastId)
                .body(body)
                .url(url)
                .build();

        surls.add(surl);

        return surl;
    }

    @GetMapping("/s/{body}/**") //**은 모든 것이 다 온다는 뜻
    @ResponseBody
    public Surl add(
            @PathVariable String body,
            HttpServletRequest req
    ){
        //String requestURI = req.getRequestURI();

        String url = req.getRequestURI();

        if(req.getQueryString()!=null){
            url += "?" + req.getQueryString();
        }

        String[] urlBits = url.split("/", 4);// "/" 기준으로 나누고, 4개 이상 나누지 마라

        System.out.println("Arrays.toString(urlBits): "+ Arrays.toString(urlBits));

        url = urlBits[3];

        Surl surl = Surl
                .builder()
                .id(++surlsLastId)
                .body(body)
                .url(url)
                .build();

        surls.add(surl);

        return surl;
    }

    @GetMapping("/g/{id}")
    //@ResponseBody
    public String go(@PathVariable long id){

        Surl foundSurl = surls
                .stream()
                .filter(s-> s.getId() == id)
                .findFirst()
                .orElse(null);

        if(foundSurl == null) throw new RuntimeException("%d번 URL을 찾을 수 없습니다.".formatted(id));

        foundSurl.increaseCount(); //사용횟수
        //ResponseBody 지우고 실행하면 바로 저장된 url로 이동
        return "redirect:"+ foundSurl.getUrl();
    }
}
