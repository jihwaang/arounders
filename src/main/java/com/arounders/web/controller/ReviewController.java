package com.arounders.web.controller;

import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Review;
import com.arounders.web.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/getReviewListOfBoard/{boardId}")
    public List<Review> getReviewListOfBoard(Model model, @PathVariable Integer boardId) {
        log.info("id : {}", boardId);
        List<Review> list = reviewService.getReviewListOfBoard(boardId);
        model.addAttribute("list", list);
        return list;
    }

    @GetMapping("/getReviewListOfMember/{memberId}")
    public List<Review> getReviewListOfMember(Model model, @PathVariable Integer memberId) {
        log.info("id : {}", memberId);
        List<Review> list = reviewService.getReviewListOfMember(memberId);
        model.addAttribute("list", list);
        return list;
    }

    @PostMapping("/createReview")
    public int createReview(Model model, @RequestBody Review Review) {
        log.info("request Review : {}", Review);
        int result = reviewService.createReview(Review);
        log.info("generated id : {}", Review.getId());
        model.addAttribute("id", Review.getId());
        return result;
    }

    @PutMapping("/editReview")
    public int editReview(Model model, @RequestBody Review Review) {
        log.info("request Review : {}", Review);
        int result = reviewService.editReview(Review);
        model.addAttribute("id", Review.getId());
        return result;
    }

    @DeleteMapping("/removeReview")
    public int removeReview(Model model, @RequestBody Review Review) {
        log.info("request : {}", Review);
        int result = reviewService.removeReview(Review);
        return result;
    }

}
