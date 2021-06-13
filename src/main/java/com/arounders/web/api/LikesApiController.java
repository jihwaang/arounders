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

    @GetMapping("/{boardId}")
    public ResponseEntity<Boolean> isLike(@PathVariable("boardId") Long boardId){

        /* test용 */
        Long memberId = 12L;
        /* 실제 사용 */
        //Long memberId = (Long) session.getAttribute("id");

        Boolean result =  service.isLike(memberId, boardId);
        log.info("#LikesApiController : isLike -> " + result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<Integer> getCounts(@PathVariable("boardId") Long boardId){

        Integer count = service.getCount(boardId);
        log.info(boardId + "번 게시글의 좋아요 갯수 : " + count + "개");

        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<Long> like(@PathVariable("boardId") Long boardId){

        /* test용 */
        Long memberId = 12L;
        /* 실제 사용 */
        //Long memberId = (Long) session.getAttribute("id");

        log.info(memberId + "번 회원이 " + boardId + "번 게시글을 좋아합니다.");
        Long id = service.like(Likes.builder().memberId(memberId).boardId(boardId).build());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<Long> dislike(@PathVariable("boardId") Long boardId){

        /* test용 */
        Long memberId = 12L;
        /* 실제 사용 */
        //Long memberId = (Long) session.getAttribute("id");

        log.info(memberId + "번 회원이 " + boardId + "번 게시글 좋아요를 취소합니다.");
        Long id = service.cancel(Likes.builder().memberId(memberId).boardId(boardId).build());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
