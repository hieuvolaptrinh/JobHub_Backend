package com.HieuVo.JobHub_BE.Service;

import com.HieuVo.JobHub_BE.DTO.Response.Job.ResponseCreateJobDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseUpdateResumeDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.Model.Job;
import com.HieuVo.JobHub_BE.Model.Skill;
import com.HieuVo.JobHub_BE.repository.CompanyRepository;
import com.HieuVo.JobHub_BE.repository.SkillRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.HieuVo.JobHub_BE.repository.JobRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final CompanyRepository companyRepository;

    public JobService(JobRepository jobRepository, SkillRepository skillRepository,
                      CompanyRepository companyRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
    }

    public Job getJobById(long id) {
        return this.jobRepository.findById(id).orElse(null);
    }

    public ResponseCreateJobDTO createJob(Job job) {

        if (job.getSkills() != null) {
            List<Long> reqSkill = job.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Skill> skill = this.skillRepository.findByIdIn(reqSkill);
            job.setSkills(skill);
        }
//        if (job.getCompany() != null) {
//            Optional<Company> company = this.companyRepository.findById(job.getCompany().getId());
//            if (company.isPresent()) {
//                job.setCompany(company.get());
//            }
//        }
        Job currentJob = this.jobRepository.save(job);
        ResponseCreateJobDTO res = new ResponseCreateJobDTO();
        res.setId(currentJob.getId());
        res.setName(currentJob.getName());
        res.setSalary(currentJob.getSalary());
        res.setQuantity(currentJob.getQuantity());
        res.setLocation(currentJob.getLocation());
        res.setLevel(currentJob.getLevel());
        res.setStartDate(currentJob.getStartDate());
        res.setEndDate(currentJob.getEndDate());
        res.setActive(currentJob.isActive());
        res.setCreatedAt(currentJob.getCreatedAt());
        res.setCreatedBy(currentJob.getCreateBy());
        res.setUpdatedAt(currentJob.getUpdatedAt());
        res.setUpdateBy(currentJob.getUpdateBy());


        if (currentJob.getSkills() != null) {
            List<String> skills = currentJob.getSkills().stream().map(item -> item.getName())
                    .collect(Collectors.toList());
            res.setSkill(skills);
        }
        return res;
    }

    public ResponseUpdateResumeDTO updateJob(Job job, Job iobInDB) {
        if (job.getSkills() != null) {
            List<Long> reqSkill = job.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Skill> dbSkills = this.skillRepository.findByIdIn(reqSkill);
            iobInDB.setSkills(dbSkills);
        }
        if (job.getCompany() != null) {
            Optional<Company> company = this.companyRepository.findById(job.getCompany().getId());
            if (company.isPresent()) {
                iobInDB.setCompany(company.get());
            }
        }
//        update correct job
        iobInDB.setName(job.getName());
        iobInDB.setSalary(job.getSalary());
        iobInDB.setQuantity(job.getQuantity());
        iobInDB.setLocation(job.getLocation());
        iobInDB.setLevel(job.getLevel());
        iobInDB.setStartDate(job.getStartDate());
        iobInDB.setEndDate(job.getEndDate());
        iobInDB.setActive(job.isActive());

//        update job
        Job currentJob = this.jobRepository.save(iobInDB);

        ResponseUpdateResumeDTO res = new ResponseUpdateResumeDTO();
        res.setUpdatedAt(currentJob.getUpdatedAt());
        res.setUpdatedBy(currentJob.getUpdateBy());
     return res;
    }

    public void deleteJob(long id) {
        this.jobRepository.deleteById(id);
    }

    public ResultPaginationDTO getAllJobs(Specification<Job> specification, Pageable pageable) {
        Page<Job> pageJob = this.jobRepository.findAll(specification, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);

        meta.setPages(pageJob.getTotalPages());
        meta.setTotal(pageJob.getTotalElements());

        rs.setMeta(meta);

        rs.setResult(pageJob.getContent());
        return rs;
    }
}
