package com.HieuVo.JobHub_BE.Service;

import java.nio.charset.StandardCharsets;



import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import javax.naming.Context;

@Service
public class EmailService {
//    @Autowired
//    private MailSender mailSender;
//
//    @Autowired
//    private JavaMailSender javaMailSender;
//
//    @Autowired
//    private TemplateEngine templateEngine;
//
//
//
//    public void sendSimpleEmail() {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo("vndhieu05@gmail.com");
//        message.setSubject("Test Mail");
//        message.setText("Hiếu Võ Send Email");
//        this.mailSender.send(message);
//    }
//
//    public void sendEmailSync(String to, String subject, String content, boolean isMultipart,
//                              boolean isHtml) {
//        // Prepare message using a Spring helper
//        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
//                    isMultipart, StandardCharsets.UTF_8.name());
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(content, isHtml);
//            this.javaMailSender.send(mimeMessage);
//        } catch (MailException | MessagingException e) {
//            System.out.println("ERROR SEND EMAIL: " + e);
//        }
//
//    }
//
//    public void sendEmailFromTemplateSync(String to, String subject, String templateName, String username,
//                                          Object value) {
//        Context context = new Context();
//        context.setVariable("name", username);
//        context.setVariable("jobs", value);
//
//        String content = this.templateEngine.process(templateName, context);
//        this.sendEmailSync(to, subject, content, false, true);
//    }

}
