package com.arounders.web.controller;

import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Interests;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.InterestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interests")
@Slf4j
public class InterestsController {

    private final InterestsService interestsService;

    public InterestsController(InterestsService interestsService) {
        this.interestsService = interestsService;
    }

    @GetMapping("/getCountOfMember/{memberId}")
    public int getCountOfMember(Model model, @PathVariable Integer memberId) {
        log.info("id : {}", memberId);
        int totalCount = interestsService.getCountOfMember(memberId);
        model.addAttribute("totalCount", totalCount);
        return totalCount;
    }

    @GetMapping("/getCountOfBoard/{boardId}")
    public int getCountOfBoard(Model model, @PathVariable Integer boardId) {
        log.info("id : {}", boardId);
        int count = interestsService.getCountOfBoard(boardId);
        model.addAttribute("count", count);
        return count;
    }

    @PostMapping("/toggleInterests")
    public int addInterests(Model model, @RequestBody Interests interests) {
        log.info("request interests : {}", interests);
        int result = interestsService.toggleInterests(interests);
        model.addAttribute("result", result);
        return result;
    }

    @DeleteMapping("/cancelInterests") // not using
    public int cancelInterests(Model model, @RequestBody Interests interests) {
        log.info("request id : {}", interests);
        int result = interestsService.cancelInterests(interests);
        model.addAttribute("result", result);
        return result;
    }

}
