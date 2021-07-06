package com.arounders.web.controller;

import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.entity.Role;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {

    private final MemberService memberService;

    private final AttachmentService attachmentService;

    private final HttpSession session;

    @GetMapping("/signup")
    public String getSignUpPage() {
        return "member/signup";
    }

    @GetMapping("/getAddrInfo")
    public String findAddrInfo(Model model, @RequestParam String platformType) {
        log.info("request url -> /member/getAddrInfo");
        String confmKey = "U01TX0FVVEgyMDIxMDYxNzA5MzEwOTExMTI5MTU=";
        if("M".equals(platformType)) confmKey = "U01TX0FVVEgyMDIxMDYxNzE4NDUwOTExMTI5NTY=";
        String resultType = "4";

        model.addAttribute("confmKey", confmKey);
        model.addAttribute("resultType", resultType);
        model.addAttribute("inputYn", "N");
        return "/member/addr-finder";
    }

    @GetMapping("/isOverlapped/nicknames/{value}")
    public @ResponseBody Integer isOverlappedNickname(@PathVariable String value) {
        log.info("nickname: {}", value);
        String field = "NICKNAME";
        return memberService.isOverlapped(field, value);
    }

    @GetMapping("/isOverlapped/email/{value}")
    public Integer isOverlappedEmail(@PathVariable String value) {
        String field = "EMAIL";
        return memberService.isOverlapped(field, value);
    }

    @GetMapping("/findAccount")
    public String findAccount() {
        log.info("findAccount invoked");
        return "/member/account-finder";
    }

    @PostMapping("/getAddrInfo")
    public String getAddrInfo(Model model, @RequestParam(name = "inputYn", required= false) String inputYn, @RequestParam(name = "roadAddrPart1") String roadAddrPart1, @RequestParam(name = "addrDetail") String addrDetail) {
        log.info("request url -> /member/getAddrInfo");
        log.info("input Yn: {}, roadFullAddr: {}, addrDetail: {}", inputYn, roadAddrPart1, addrDetail);
        model.addAttribute("inputYn", inputYn);
        model.addAttribute("roadAddrPart1", roadAddrPart1);
        model.addAttribute("addrDetail", addrDetail);
        return "/member/addr-finder";
    }

    @PostMapping("/signup")
    public String doSignUp(MemberDTO requestMember, HttpSession session) {
      Long id = memberService.signup(requestMember);
      log.info("generated id: {}", id);
      return "/index";
    }

    @PostMapping("/checkExist")
    @ResponseBody
    public Integer checkExistingMember(@RequestBody MemberDTO member) {
        log.info("checkExistingMember invoked, request: {}", member);
        return memberService.countByEmailandNickName(member);
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public int checkEmail(@RequestBody String email) {
        log.info("request url -> /member/checkEmail, email: {}", email);
        return memberService.countByEmail(email);
    }

    @PostMapping("checkPassword")
    @ResponseBody
    public int checkPassword(MemberDTO memberDTO) {
        log.info("request url -> /member/checkPassword, memberDTO: {}", memberDTO);
        memberDTO.setId((Long) session.getAttribute("id"));
        return memberService.checkPassword(memberDTO);
    }

    @PostMapping("/update/password")
    @ResponseBody
    public int changePassword(@RequestBody MemberDTO member) {
        log.info("change password invoked");
        log.info("member: {}", member);
        return memberService.updatePassword(member);
    }

}
