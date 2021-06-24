package com.arounders.web.controller;

import com.arounders.web.entity.Category;
import com.arounders.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/mypage")
public class MypageController {

    private final BoardService boardService;

    @GetMapping(value = "/dashboard")
    public String dashboard(Model model){

        /* Test용 */
        Long memberId = 12L;
        /* Dev용 */
        //Long memberId = (Long) session.getAttribute("id");

        Map<String, Integer> countMap = boardService.getCountListByCategory(memberId);

        model.addAttribute("countMap", countMap);

        log.info("#MypageController -> dashboard : ");
        countMap.forEach(log::info);

        return "mypage/dashboard";
    }

    @GetMapping(value = "/comments")
    public String comments(){

        return "mypage/comments";
    }

    @GetMapping(value = "/rooms")
    public String rooms(){

        return "mypage/rooms";
    }
}
