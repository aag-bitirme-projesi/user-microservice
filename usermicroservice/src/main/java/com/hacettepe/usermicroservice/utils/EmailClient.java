package com.hacettepe.usermicroservice.utils;

import com.hacettepe.usermicroservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class EmailClient {
    private final JavaMailSender javaMailSender;
    public void constructEmail(String contextPath, String token, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String emailMessage = "Here's the link for resetting your password: %s";
        String url = contextPath + "/user/changePassword?token=" + token;
        message.setText(String.format(emailMessage, url));

        sendEmail(message, email);
    }

    public void sendEmail(SimpleMailMessage message, String email)
    {
        message.setFrom("${spring.mail.username}");
        message.setTo(email);
        message.setSubject("Reminder!");
        javaMailSender.send(message);
    }
}
