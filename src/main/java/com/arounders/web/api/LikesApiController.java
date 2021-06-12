package com.arounders.web.api;

import com.arounders.web.entity.Likes;
import com.arounders.web.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/likes/api/v1")
@Log4j2
@RequiredArgsConstructor
public class LikesApiController {

    private final LikesService service;

    @PostMapping("/members/{memberId}/boards/{boardId}")
    public ResponseEntity<String> like(@PathVariable("memberId") Long memberId,
                               @PathVariable("boardId") Long boardId){

        log.info(memberId + "번 회원이 " + boardId + "번 게시글을 좋아합니다.");
        service.like(Likes.builder().memberId(memberId).boardId(boardId).build());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    @DeleteMapping("/members/{memberId}/boards/{boardId}")
    public ResponseEntity<String> dislike(@PathVariable("memberId") Long memberId,
                                       @PathVariable("boardId") Long boardId){

        log.info(memberId + "번 회원이 " + boardId + "번 게시글 좋아요를 취소합니다.");
        service.cancel(Likes.builder().memberId(memberId).boardId(boardId).build());

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
