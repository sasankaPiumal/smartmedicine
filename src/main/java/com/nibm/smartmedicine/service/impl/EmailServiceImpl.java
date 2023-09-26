package com.nibm.smartmedicine.service.impl;

import com.nibm.smartmedicine.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Async
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        System.out.println(text);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("smart.medi.srilanka@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

}
