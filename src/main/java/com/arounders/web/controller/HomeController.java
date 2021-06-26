package com.arounders.web.controller;

import com.arounders.web.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String index(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /, session user id: {}", id);
        if(id != null) return "redirect:/board/list";
        return "/index";
    }
    @PostMapping("/login")
    public String getIndex() {
        log.info("request url -> /login");
        return "/board/list";
    }
}
