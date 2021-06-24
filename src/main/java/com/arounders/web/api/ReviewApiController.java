package com.arounders.web.api;

import com.arounders.web.dto.ReviewDTO;
import com.arounders.web.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/reviews/api/v1")
@RequiredArgsConstructor
@Log4j2
public class ReviewApiController {

    private final ReviewService reviewService;
    private final HttpSession session;

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<List<ReviewDTO>> getReviewListOfBoard(@PathVariable("boardId") Long boardId) {

        log.info("#ReviewApiController -> getReviewListOfBoard : " + boardId);
        List<ReviewDTO> list = reviewService.getReviewListOfBoard(boardId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<List<ReviewDTO>> getReviewListOfMember(@PathVariable("memberId") Long memberId) {

        log.info("#ReviewApiController -> getReviewListOfMember : " + memberId);
        List<ReviewDTO> list = reviewService.getReviewListOfMember(memberId);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {

        /* test용 */
        //reviewDTO.setMemberId(12L);
        /* 실제 사용 */
        reviewDTO.setMemberId((Long) session.getAttribute("id"));

        log.info("request Review : {}", reviewDTO);
        Long id = reviewService.createReview(reviewDTO);
        log.info("generated id : " + id);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Long> editReview(@PathVariable("reviewId") Long reviewId,
                                           @RequestBody ReviewDTO reviewDTO) {

        reviewDTO.setId(reviewId);
        log.info("request Review : {}", reviewDTO);
        Long id = reviewService.editReview(reviewDTO);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Long> removeReview(@PathVariable("reviewId") Long reviewId) {

        log.info("request : {}", reviewId);
        Long id = reviewService.removeReview(reviewId);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
