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
    public ResponseEntity<List<ReviewDTO>> getReviewListOfBoard(@PathVariable("boardId") Long boardId, @RequestParam("offset") Long offset) {
        log.info("offset: {}", offset);
        log.info("#ReviewApiController -> getReviewListOfBoard : " + boardId);
        List<ReviewDTO> list = reviewService.getReviewListOfBoard(boardId, offset);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDTO> getReview(@PathVariable("reviewId") Long id) {
        Long memberId = (Long) session.getAttribute("id");
        log.info("requestURL: /reviews/api/v1/review/{reviewId} , reviewId: {}", id);
        ReviewDTO reviewDTO = reviewService.getReviewOfMineOfBoard(id);
        return new ResponseEntity<>(reviewDTO, HttpStatus.OK);
    }

    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewDTO>> getReviewListOfMember(@RequestParam("offset") Long offset) {
        Long memberId = (Long) session.getAttribute("id");
        log.info("#ReviewApiController -> getReviewListOfMember : {}, offset: {}", memberId, offset);
        List<ReviewDTO> list = reviewService.getReviewListOfMember(memberId, offset);
        log.info("my reviewList: {}", list);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createReview(@RequestBody ReviewDTO reviewDTO) {

        /* test??? */
        //reviewDTO.setMemberId(12L);
        /* ?????? ?????? */
        reviewDTO.setMemberId((Long) session.getAttribute("id"));

        log.info("request Review : {}", reviewDTO);
        Long id = reviewService.createReview(reviewDTO);
        log.info("generated id : " + id);

        return id != null? new ResponseEntity<>(id, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("duplicates")
    public int checkDuplicates(@RequestBody ReviewDTO reviewDTO) {
        reviewDTO.setMemberId((Long) session.getAttribute("id"));
        log.info("request URL: /reviews/api/v1/duplicates, method: POST, reviewDTO: {}", reviewDTO);
        return reviewService.getCountDups(reviewDTO);
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
