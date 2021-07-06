package com.arounders.web.controller;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {

    private final MemberService memberService;
    private final AttachmentService attachmentService;

    @GetMapping("/")
    public String index(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /, session user id: {}", id);
        if(id != null) return "redirect:/board/list";
        return "/index";
    }

    @GetMapping("/login")
    public String login(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        String profileImg = attachmentService.findProfileImgPathById(id);
        log.info("request url -> /, session user id: {}", id);
        if(id != null) {
            Member user = memberService.getMember(id);
            /* individual values to session settings */
            session.setAttribute("id", user.getId());
            session.setAttribute("nickname", user.getNickname());
            session.setAttribute("region", user.getAddr());
            session.setAttribute("role", user.getRoleId());
            session.setAttribute("profileImg", profileImg);
            return "redirect:/board/list";
        }
        return "/index";
    }

    @PostMapping("/login")
    public String getIndex(MemberDTO requestMember, HttpSession session) {
        log.info("request url(POST) -> /login");
        Long id = memberService.signup(requestMember);
        log.info("id: {}", id);
        return "/board/list";
    }
}
