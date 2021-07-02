package com.arounders.web.api;

import com.arounders.web.entity.Interests;
import com.arounders.web.service.InterestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/interests/api/v1")
@Log4j2
@RequiredArgsConstructor
public class InterestsApiController {

    private final InterestsService interestsService;
    private final HttpSession session;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<Integer> getCountOfMember(@PathVariable("memberId") Long memberId) {

        log.info("id : {}", memberId);
        Integer total = interestsService.getCountOfMember(memberId);

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<Integer> getCountOfBoard(@PathVariable("boardId") Long boardId) {

        log.info("id : {}", boardId);
        int total = interestsService.getCountOfBoard(boardId);

        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Long> addInterests(@PathVariable("boardId") Long boardId) {

        Long memberId = (Long) session.getAttribute("id");

        int result = interestsService.toggleInterests(Interests.builder().memberId(memberId).boardId(boardId).build());

        return result > 0? new ResponseEntity<>(boardId, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @DeleteMapping("/cancelInterests") // not using
//    public int cancelInterests(Model model, @RequestBody Interests interests) {
//        log.info("request id : {}", interests);
//        int result = interestsService.cancelInterests(interests);
//        model.addAttribute("result", result);
//        return result;
//    }

}
