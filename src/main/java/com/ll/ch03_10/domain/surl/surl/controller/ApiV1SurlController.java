package com.ll.ch03_10.domain.surl.surl.controller;

import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.surl.surl.dto.SurlDto;
import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import com.ll.ch03_10.domain.surl.surl.service.SurlService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.global.rq.Rq;
import com.ll.ch03_10.global.rsData.RsData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ApiV1SurlController {
    private final Rq rq;
    private final SurlService surlService;

    @AllArgsConstructor
    @Getter
    public static class SurlAddReqBody{
        @NotBlank
        private String body;
        @NotBlank
        private String url;
    }

    @AllArgsConstructor
    @Getter
    public static class SurlAddRespBody{
        private SurlDto item;
    }

    @PostMapping("")
    @Transactional
    public RsData<SurlAddRespBody> add(
            @RequestBody @Valid SurlAddReqBody reqBody
    ){
        Member member = rq.getMember(); //현재 브라우저로 로그인한 회원
        RsData<Surl> addRs = surlService.add(member, reqBody.body, reqBody.url);

        return addRs.newDataOf(
                new SurlAddRespBody(
                        new SurlDto(addRs.getData())
                )
        );
    }


    @AllArgsConstructor
    @Getter
    public static class SurlGetRespBody{
        private SurlDto item;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public RsData<SurlGetRespBody> get(
            @PathVariable long id
    ){
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        return RsData.of(
                new SurlGetRespBody(
                        new SurlDto(surl)
                )
        );
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


}
