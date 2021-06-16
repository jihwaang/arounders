package com.arounders.web.serviceImpl;

import com.arounders.web.dto.RequestMail;
import com.arounders.web.entity.EmailAuth;
import com.arounders.web.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@RequiredArgsConstructor
@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Override
    public Long sendMail(RequestMail mail) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        String htmlMsg = "";
        try {
            messageHelper.setText(htmlMsg, true);
            messageHelper.setTo("jihwaang@naver.com");
            messageHelper.setSubject("Email Test");
            messageHelper.setFrom("Arounders");

            mailSender.send(message);
            return 1L;
        } catch (MessagingException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    @Override
    public Long sendAuthMail(EmailAuth mail) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "utf-8");
        String url = "http://localhost:8080/emailAuth/confirm?confirmKey="+mail.getConfirmKey();
        String onClick = "onclick=\"alert('회원가입을 마무리해주세요 :)')\"";
        String htmlMsg = "<form style=\"width: 550px;\n" +
                "      height: 200px;\n" +
                "      display: flex;\n" +
                "      flex-direction: column;\n" +
                "      place-items: center;\">\n" +
                "    <h1>Arounder에 오신것을 환영합니다.</h1>\n" +
                "    <p>회원가입 인증을 위해 아래 인증버튼을 눌러주세요.</p></br></br>\n" +
                /*"    <div style=\"width: 200px;\n" +
                "        height: 50px;\n" +
                "        background-color: skyblue;\n" +
                "        border: none;\n" +
                "        color: #fff;\n" +
                "        font-size: 1rem;\n" +
                "        cursor: pointer;\n" +
                "        display: flex;\n" +
                "        align-items: center;\n" +
                "        justify-content: center;\">\n" +*/
                "        <a href=\""+url+"\" style=\"color: #fff; text-decoration: none; font-weight: bold; display: block; width: 200px; height: 50px; background-color: skyblue;\">인증하기</a>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "</form>"
                ;
        try {
            messageHelper.setText(htmlMsg, true);
            messageHelper.setTo("jihwaang@naver.com");
            messageHelper.setSubject("Email Test");
            messageHelper.setFrom("Arounders");

            mailSender.send(message);
            return 1L;
        } catch (MessagingException e) {
            e.printStackTrace();
            return -1L;
        }
    }

}
