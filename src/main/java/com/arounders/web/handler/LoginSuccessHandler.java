package com.arounders.web.handler;

import com.arounders.web.entity.Member;
import com.arounders.web.repository.MemberRepository;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.MemberService;
import com.arounders.web.serviceImpl.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
@Service
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AttachmentService attachmentService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        Member user = memberService.getMemberByEmail(authentication.getName());
        log.info("user info: {}", user);
        int result = memberService.updateLastLogin(user);
        // set user address with index number 1
        if (user.getAddr() != null && user.getAddr().isBlank()) {
            user.setAddr(user.getAddr().split(" ")[1]);
        }

        /* get profile image path */
        String profileImg = attachmentService.findProfileImgPathById(user.getId());

        session.setAttribute("user", user);
        /* individual values to session settings */
        session.setAttribute("id", user.getId());
        session.setAttribute("nickname", user.getNickname());
        session.setAttribute("region", user.getAddr());
        session.setAttribute("role", user.getRoleId());
        session.setAttribute("profileImg", profileImg);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
