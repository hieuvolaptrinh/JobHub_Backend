package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.Service.EmailService;
import com.HieuVo.JobHub_BE.Service.SubscriberService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    private final SubscriberService subscriberService;

    public EmailController(
            SubscriberService subscriberService) {

        this.subscriberService = subscriberService;
    }

    @GetMapping("/email")
//    @Scheduled(cron = "*/10 * * * * *") //: 10s 1 lan
    @ApiMessage("Send email success")
    public String sendEmail() {
//        emailService.sendSimpleEmail();
//         this.emailService.sendEmailSync("vndhieuak@gmail.com", "test send email",
//                 "<h1> HIeu Vo</h1>", true,
//                 true);

//        this.emailService.sendEmailFromTemplateSync("vndhieuak@gmail.com",
//                "test send email", "test", "hieu vo",
//                true);

        this.subscriberService.sendSubscribersEmailJobs();
        return "Send email success";

    }
}
