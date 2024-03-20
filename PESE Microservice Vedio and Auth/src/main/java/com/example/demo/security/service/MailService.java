package com.example.demo.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.ResourceLoader;

@Slf4j
@Service
public class MailService {

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    ResourceLoader resourceLoader;

    @Value("${spring.mail.verify-cd}")
    int verifyCdSec;

    @Value("${spring.mail.verify-available}")
    int verifyAvailableMin;

    @Value("${spring.mail.username}")
    private String from;

    public void sendTextMail(String to, String subject, String text) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try {
            mailSender.send(message);
            log.info("简单邮件已经发送。");
        } catch (MailException e) {
            log.error("发送简单邮件时发生异常！", e);
            throw e;
        }
    }
}
