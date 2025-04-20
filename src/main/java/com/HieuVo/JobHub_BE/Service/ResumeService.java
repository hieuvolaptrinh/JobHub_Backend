package com.HieuVo.JobHub_BE.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseCreateResumeDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseGetResumeDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseUpdateResumeDTO;
import com.HieuVo.JobHub_BE.Model.Job;
import com.HieuVo.JobHub_BE.Model.Resume;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;
import com.HieuVo.JobHub_BE.repository.JobRepository;
import com.HieuVo.JobHub_BE.repository.ResumeRepository;
import com.HieuVo.JobHub_BE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecification;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import com.turkraft.springfilter.parser.FilterParser;
import com.turkraft.springfilter.parser.node.FilterNode;



@Service
public class ResumeService {
    @Autowired
    FilterBuilder fb;

    @Autowired
    private FilterParser filterParser;

    @Autowired
    private FilterSpecificationConverter filterSpecificationConverter;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;

    public ResumeService(UserRepository userRepository, JobRepository jobRepository,
                         ResumeRepository resumeRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.resumeRepository = resumeRepository;
    }

    public Optional<Resume> getResumeById(long id) {
        return this.resumeRepository.findById(id);
    }

    public boolean isExistByUserAndJob(Resume resume) {
        if (resume.getUser() == null) {
            return false;
        }
        Optional<User> user = this.userRepository.findById(resume.getUser().getId());
        if (user.isEmpty()) {
            return false;
        }
        if (resume.getJob() == null) {
            return false;
        }
        Optional<Job> job = this.jobRepository.findById(resume.getJob().getId());
        if (job.isEmpty()) {
            return false;
        }
        return true;
    }

    public ResponseCreateResumeDTO createResume(Resume resume) {
        resume = this.resumeRepository.save(resume);
        ResponseCreateResumeDTO res = new ResponseCreateResumeDTO();
        res.setId(resume.getId());
        res.setCreatedAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        return res;
    }

    public ResponseUpdateResumeDTO updateResume(Resume resume) {
        resume = this.resumeRepository.save(resume);
        ResponseUpdateResumeDTO res = new ResponseUpdateResumeDTO();
        res.setUpdatedAt(resume.getUpdatedAt());
        res.setUpdatedBy(resume.getUpdatedBy());
        return res;
    }

    public void deleteResume(long id) {
        this.resumeRepository.deleteById(id);
    }

    public ResponseGetResumeDTO getResumeById(Resume resume) {
        ResponseGetResumeDTO res = new ResponseGetResumeDTO();
        res.setId(resume.getId());
        res.setEmail(resume.getEmail());
        res.setUrl(resume.getUrl());
        res.setStatus(resume.getStatus());
        res.setCreatedAt(resume.getCreatedAt());
        res.setCreatedBy(resume.getCreatedBy());
        res.setUpdatedAt(resume.getUpdatedAt());
        res.setUpdatedBy(resume.getUpdatedBy());
        if (resume.getJob() != null) {
            res.setCompanyName(resume.getJob().getCompany().getName());
        }
        res.setUser(new ResponseGetResumeDTO.UserResume(resume.getUser().getId(), resume.getUser().getName()));
        res.setJob(new ResponseGetResumeDTO.JobResume(resume.getJob().getId(), resume.getJob().getName()));
        return res;
    }

    public ResultPaginationDTO getAllResume(Specification<Resume> specification, Pageable pageable) {
        Page<Resume> pageResume = this.resumeRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageResume.getTotalPages());
        meta.setTotal(pageResume.getTotalElements());
        rs.setMeta(meta);
        List<ResponseGetResumeDTO> listResume = pageResume.getContent().stream().map(item -> this.getResumeById(item))
                .collect(Collectors.toList());
        rs.setResult(listResume);

        return rs;
    }

    public ResultPaginationDTO fetchResumeByUser(Pageable pageable) {
        // query builder
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        FilterNode node = filterParser.parse("email='" + email + "'");
        FilterSpecification<Resume> spec = filterSpecificationConverter.convert(node);
        Page<Resume> pageResume = this.resumeRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageSize() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageResume.getTotalPages());
        meta.setTotal(pageResume.getTotalElements());

        rs.setMeta(meta);

        // remove sensitive data
        List<ResponseGetResumeDTO> listResume = pageResume.getContent()
                .stream().map(item -> this.getResumeById(item))
                .collect(Collectors.toList());

        rs.setResult(listResume);

        return rs;
    }
}
