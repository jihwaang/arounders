package com.arounders.web.api;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.CommentDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.dto.criteria.CommentCriteria;
import com.arounders.web.service.BoardService;
import com.arounders.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/mypages/api/v1")
@Log4j2
public class MypageApiController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping(value = "/boards")
    public ResponseEntity<List<BoardDTO>> getMyBoards(BoardCriteria criteria){

        /* Test용 */
        Long memberId = 12L;
        /* Dev용 */
        //Long memberId = (Long) session.getAttribute("id");

        criteria.init();
        List<BoardDTO> myList = boardService.getMyList(criteria, memberId);
        log.info("#MypageApiController -> getMyBoards : ...");

        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    /* 내가 쓴 댓글 목록 조회 */
    @GetMapping(value = "/comments")
    public ResponseEntity<List<CommentDTO>> getMyComments(CommentCriteria criteria){

        /* test용 id */
        Long memberId = 12L;
        /* 실제 사용될 session에 저장된 id */
        //Long memberId = (Long) session.getAttribute("id");

        criteria.init();
        List<CommentDTO> comments = commentService.getMyComments(memberId, criteria);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
