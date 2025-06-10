package com.grepp.synapse4.app.model.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendTempPasswordEmail(String toEmail, String tempPassword) {
        String subject = "[밥로드] 임시 비밀번호 안내";
        String content = "요청하신 임시 비밀번호는 아래와 같습니다.\n\n"
            + "임시 비밀번호: " + tempPassword + "\n\n"
            + "로그인 후 반드시 비밀번호를 변경해주세요.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);

        mailSender.send(message);
    }
}
