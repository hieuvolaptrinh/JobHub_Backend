package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.Model.Subscriber;
import com.HieuVo.JobHub_BE.Service.SubscriberService;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    public ResponseEntity<Subscriber> createSubscriber(@Valid @RequestBody Subscriber subcriber)
            throws IdInvalidException {
        boolean isExist = this.subscriberService.existsByEmail(subcriber.getEmail());
        if (isExist == true) {
            throw new IdInvalidException("Email is already exist");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subscriberService.createSubscriber(subcriber));
    }

    @PutMapping("/subscribers")
    public ResponseEntity<Subscriber> updateSubscriber(@Valid @RequestBody Subscriber subscriber)
            throws IdInvalidException {
        Subscriber currentSubcriber = this.subscriberService.getSubcriberById(subscriber.getId());
        if (currentSubcriber == null) {
            throw new IdInvalidException("Subscriber not found");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.subscriberService.updateSubscriber(currentSubcriber, subscriber));
    }

    @PostMapping("/subscribers/skills")
    public ResponseEntity<Subscriber> getSubscribersSkill() {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        return ResponseEntity.ok().body(this.subscriberService.findByEmail(email));
    }

}
