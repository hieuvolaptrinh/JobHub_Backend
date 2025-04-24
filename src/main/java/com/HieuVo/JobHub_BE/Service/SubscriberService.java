package com.HieuVo.JobHub_BE.Service;


import com.HieuVo.JobHub_BE.Model.Skill;
import com.HieuVo.JobHub_BE.Model.Subscriber;
import com.HieuVo.JobHub_BE.repository.JobRepository;
import com.HieuVo.JobHub_BE.repository.SkillRepository;
import com.HieuVo.JobHub_BE.repository.SubscriberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    private final SubscriberRepository subscriberRepository;
    private final SkillRepository skillRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    public SubscriberService(SubscriberRepository subscriberRepository, SkillRepository skillRepository,
                             JobRepository jobRepository, EmailService emailService) {
        this.subscriberRepository = subscriberRepository;
        this.skillRepository = skillRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

    public boolean existsByEmail(String email) {
        return this.subscriberRepository.existsByEmail(email);
    }

    public Subscriber findByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

    public Subscriber createSubscriber(Subscriber subcriber) {
        if (subcriber.getSkills() != null) {
            List<Long> reqSkill = subcriber.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Skill> currentSkill = this.skillRepository.findByIdIn(reqSkill);
            subcriber.setSkills(currentSkill);
        }
        return this.subscriberRepository.save(subcriber);
    }

    public Subscriber getSubcriberById(long id) {
        Optional<Subscriber> currentSubscriber = this.subscriberRepository.findById(id);
        if (currentSubscriber.isPresent()) {
            return currentSubscriber.get();
        }
        return null;
    }

    public Subscriber updateSubscriber(Subscriber SubscriberDB, Subscriber SubscriberReq) {

        if (SubscriberReq.getSkills() != null) {
            List<Long> reqSkill = SubscriberReq.getSkills().stream().map(x -> x.getId())
                    .collect(Collectors.toList());
            List<Skill> currentSkill = this.skillRepository.findByIdIn(reqSkill);
            SubscriberDB.setSkills(currentSkill);
        }
        return this.subscriberRepository.save(SubscriberDB);
    }

//    public ResEmailJob convertJobToSendEmail(Job job) {
//        ResEmailJob res = new ResEmailJob();
//        res.setName(job.getName());
//        res.setSalary(job.getSalary());
//        res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
//        List<Skill> skills = job.getSkills();
//        List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
//                .collect(Collectors.toList());
//        res.setSkills(s);
//        return res;
//    }
//
//    @Async
//    @Transactional
//    public void sendSubscribersEmailJobs() {
//        List<Subcriber> listSubs = this.subscriberRepository.findAll();
//        if (listSubs != null && listSubs.size() > 0) {
//            for (Subcriber sub : listSubs) {
//                List<Skill> listSkills = sub.getSkills();
//                if (listSkills != null && listSkills.size() > 0) {
//                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
//                    if (listJobs != null && listJobs.size() > 0) {
//                        List<ResEmailJob> arr = listJobs.stream().map(
//                                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());
//                        this.emailService.sendEmailFromTemplateSync(
//                                sub.getEmail(),
//                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
//                                "job",
//                                sub.getName(),
//                                arr);
//                    }
//                }
//            }
//        }
//    }

}
