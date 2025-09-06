package com.ll.ch03_10.domain.surl.surl.controller;

import com.ll.ch03_10.domain.auth.auth.service.AuthService;
import com.ll.ch03_10.domain.member.member.entity.Member;
import com.ll.ch03_10.domain.member.member.service.MemberService;
import com.ll.ch03_10.domain.surl.surl.dto.SurlDto;
import com.ll.ch03_10.domain.surl.surl.entity.Surl;
import com.ll.ch03_10.domain.surl.surl.service.SurlService;
import com.ll.ch03_10.global.exceptions.GlobalException;
import com.ll.ch03_10.global.rq.Rq;
import com.ll.ch03_10.global.rsData.RsData;
import com.ll.ch03_10.standard.dto.Empty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/surls")
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
@Tag(name = "ApiSurlController", description = "Surl CRUD 컨트롤러")
public class ApiV1SurlController {
    private final Rq rq;
    private final SurlService surlService;
    private final AuthService authService;
    private final MemberService memberService;

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
    @Operation(summary = "생성")
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
    @Operation(summary = "단건조회")
    public RsData<SurlGetRespBody> get(
            @PathVariable long id
    ){
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        authService.checkCanGetSurl(rq.getMember(), surl);

        return RsData.of(
                new SurlGetRespBody(
                        new SurlDto(surl)
                )
        );
    }


    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "삭제")
    public RsData<Empty> delete(
            @PathVariable long id
    ){
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);

        authService.checkCanDeleteSurl(rq.getMember(), surl);

        surlService.delete(surl);

        return RsData.OK;
    }



    @AllArgsConstructor
    @Getter
    public static class SurlGetItemsRespBody {
        private List<SurlDto> items;
    }

    @GetMapping("")
    @ResponseBody
    @Operation(summary = "다건조회")
    public RsData<SurlGetItemsRespBody> getItems(){

        Member member = rq.getMember();
        List<Surl> surls = surlService.findByAuthorOrderByIdDesc(member);

        return RsData.of(
                new SurlGetItemsRespBody(
                       surls.stream()
                               .map(SurlDto::new)
                               .toList()
                )
        );
    }



    @AllArgsConstructor
    @Getter
    public static class SurlModifyReqBody{
        @NotBlank
        private String body;
        @NotBlank
        private String url;
    }

    @AllArgsConstructor
    @Getter
    public static class SurlModifyRespBody{
        private SurlDto item;
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "수정")
    public RsData<SurlModifyRespBody> modify(
            @PathVariable long id,
            @RequestBody @Valid SurlModifyReqBody reqBody
    ){
        Surl surl = surlService.findById(id).orElseThrow(GlobalException.E404::new);
        RsData<Surl> modifyRs = surlService.modify(surl, reqBody.body, reqBody.url);

        authService.checkCanModifySurl(rq.getMember(), surl);

        return modifyRs.newDataOf(
                new SurlModifyRespBody(
                        new SurlDto(modifyRs.getData())
                )
        );
    }

}
