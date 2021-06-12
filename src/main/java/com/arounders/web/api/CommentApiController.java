package com.arounders.web.api;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments/api/v1")
@Log4j2
public class CommentApiController {

    private final CommentService service;

    /* 특정 댓글 조회 */
    @GetMapping(value = "/{commentId}")
    public ResponseEntity<CommentDTO> getComment(@PathVariable("commentId") Long commentId){

        CommentDTO comment = service.getComment(commentId);
        log.info("#CommentApiController : getComment -> " + comment);

        return comment != null? new ResponseEntity<>(comment, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 댓글 수정 */
    @PutMapping(value = "/{commentId}")
    public ResponseEntity<Long> modify(@PathVariable("commentId") Long commentId,
                                       @RequestBody CommentDTO commentDTO){

        commentDTO.setId(commentId);
        Long id = service.update(commentDTO);
        log.info("#CommentApiController : modify -> " + id);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 댓글 삭제 */
    @DeleteMapping(value = "/{commentId}")
    public ResponseEntity<Long> remove(@PathVariable("commentId") Long commentId){

        Long id = service.delete(commentId);
        log.info("#CommentApiController : delete -> " + id);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 특정 게시글의 댓글 목록 조회 */
    @GetMapping(value = "/boards/{boardId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable("boardId") Long boardId){

        List<CommentDTO> comments = service.getComments(boardId);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    /* 댓글 작성 */
    @PostMapping(value = "/boards/{boardId}")
    public ResponseEntity<Long> register(@PathVariable("boardId") Long boardId,
                                         @RequestBody CommentDTO commentDTO){

        /*test용*/
        commentDTO.setMemberId(12L);
        commentDTO.setNickname("admin");

        /*session의 memberId와 nickname 넣기
        commentDTO.setMemberId(session.getAttribute("id");
        commentDTO.setNickname(session.getAttribute("nickname");
        */
        commentDTO.setBoardId(boardId);
        log.info("#CommentApiController -> register : " + commentDTO);
        Long id = service.create(commentDTO);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 대댓글 작성 */
    @PostMapping(value = "/boards/{boardId}/{commentId}")
    public ResponseEntity<Long> registerRe(@PathVariable("boardId") Long boardId,
                                           @PathVariable("commentId") Long upperId,
                                           @RequestBody CommentDTO commentDTO){

        /*test용*/
        commentDTO.setMemberId(12L);
        commentDTO.setNickname("admin");

        /*session의 memberId와 nickname 넣기
        commentDTO.setMemberId(session.getAttribute("id");
        commentDTO.setNickname(session.getAttribute("nickname");
        */
        commentDTO.setBoardId(boardId);
        commentDTO.setUpperId(upperId);
        log.info("#CommentApiController -> registerRe : " + commentDTO);
        Long id = service.create(commentDTO);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* 내가 쓴 댓글 목록 조회 */
    @GetMapping(value = "")
    public ResponseEntity<List<CommentDTO>> getMyComments(){

        /* test용 id */
        Long id = 12L;
        /* 실제 사용될 session에 저장된 id */
        //Long id = (Long) session.getAttribute("id");

        List<CommentDTO> comments = service.getMyComments(id);

        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
