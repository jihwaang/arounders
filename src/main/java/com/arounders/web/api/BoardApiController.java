package com.arounders.web.api;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/boards/api/v1")
@Log4j2
public class BoardApiController {

    private final BoardService service;
    private final HttpSession session;

    @GetMapping(value = "")
    public ResponseEntity<List<BoardDTO>> getList(BoardCriteria criteria){

        Long memberId = (Long) session.getAttribute("id");

        criteria.init();
        //Long startTime = System.currentTimeMillis();
        List<BoardDTO> list = service.getList(memberId, criteria);
        //Long endTime = System.currentTimeMillis();

        //System.out.println((endTime - startTime) + "ms");

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /* 게시글 숨기기 (작성자 본인만 가능) */
    @PostMapping(value = "/{boardId}")
    public ResponseEntity<Long> doHide(@PathVariable("boardId") Long boardId){

        Long id = service.hideBoard(boardId);

        return id == boardId? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 게시글 숨기기 해제 (작성자 본인만 가능) */
    @PutMapping(value = "/{boardId}")
    public ResponseEntity<Long> doShow(@PathVariable("boardId") Long boardId){

        Long id = service.showBoard(boardId);

        return id == boardId? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* 게시글 진행중 -> 종료 */
    @PostMapping("/done/{boardId}")
    public ResponseEntity<Long> done(@PathVariable("boardId") Long boardId){

        Long id = service.done(boardId);

        return id == boardId? new ResponseEntity<>(id, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
