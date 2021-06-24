package com.arounders.web.controller;
import com.arounders.web.entity.Category;
import com.arounders.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.arounders.web.dto.MemberDTO;
import com.arounders.web.entity.Category;
import com.arounders.web.entity.Member;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.BoardService;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    private final MemberService memberService;

    private final AttachmentService attachmentService;

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

    @GetMapping("/location")
    public String getUserLocation(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /mypage/location, session user id: {}", id);
        return "mypage/my-location";
    }

    @GetMapping("/info")
    public String getInfo(Model model, HttpSession session) {

        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /mypage/info, session user id: {}", id);

        String profileImg = (String) session.getAttribute("profileImg");
        log.info("request url -> /mypage/info, session profileImg {}", profileImg);

        /* get MemberInfo to put into form data set as default here */
        Member member = memberService.getMember(id);
        model.addAttribute("member", member);

        return "mypage/myinfo";
    }

    @PostMapping("/update/info")
    @ResponseBody
    public int updateMemberInfo(MemberDTO memberDTO, @RequestParam(name="profileImg")MultipartFile multipartFile, HttpSession session) {
        log.info("request url -> /mypage/update/info, memberDTO: {}, multipartFile: {}"
                , memberDTO, multipartFile.toString());

        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /mypage/update/info, session user id: {}", id);

        String profileImg = (String) session.getAttribute("profileImg");
        log.info("request url -> /mypage/update/info, session profileImg {}", profileImg);

        /* set member id from session */
        memberDTO.setId(id);

        /* get real path then update Member Info */
        String realPath = session.getServletContext().getRealPath("/upload");

        /* updateMemberInfo here*/
        int result = memberService.updateMember(memberDTO, multipartFile, realPath);

        /* reset session if successful */
        if (result > 0) resetSession(session);

        return result;
    }

    @PostMapping("/update/address")
    public String updateAddress(MemberDTO memberDTO, HttpSession session) {

        Long id = (Long) session.getAttribute("id");
        log.info("request url -> /mypage/update/address, session user id: {}, memberDTO: {}",
                memberDTO, id);

        /* DTO settings */
        memberDTO.setId(id);
        memberDTO.setCityId(memberService.findCityId(memberDTO.getAddr()));

        /* updateAddress here */
        int result = memberService.updateAddress(memberDTO);
        /* session refresh */
        if (result > 0) resetSession(session);

        return "mypage/my-location";
    }

    public void resetSession(HttpSession session) {
        Long id = (Long) session.getAttribute("id");

        /* get user info */
        Member user = memberService.getMember(id);
        /* get profile image path */
        String profileImg = attachmentService.findProfileImgPathById(id);

        session.setAttribute("id", user.getId());
        session.setAttribute("nickname", user.getNickname());
        session.setAttribute("region", user.getAddr().split(" ")[1]);
        session.setAttribute("role", user.getRoleId());
        session.setAttribute("profileImg", profileImg);
        log.info("reset session user info id : {}, nickname: {}, region: {}, role: {}, profileImg: {}",
                user.getId(), user.getNickname(), user.getAddr(), user.getRoleId(), profileImg);
    }
}

