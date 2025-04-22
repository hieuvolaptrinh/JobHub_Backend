package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseCreateResumeDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseGetResumeDTO;
import com.HieuVo.JobHub_BE.DTO.Response.Resume.ResponseUpdateResumeDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.Model.Job;
import com.HieuVo.JobHub_BE.Model.Resume;
import com.HieuVo.JobHub_BE.Model.User;
import com.HieuVo.JobHub_BE.Service.ResumeService;
import com.HieuVo.JobHub_BE.Service.UserService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.HieuVo.JobHub_BE.Util.SecurityUtil;
import com.turkraft.springfilter.boot.Filter;
import com.turkraft.springfilter.builder.FilterBuilder;
import com.turkraft.springfilter.converter.FilterSpecificationConverter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;
    private final UserService userService;
    private final FilterSpecificationConverter filterSpecificationConverter;
    private final FilterBuilder filterBuilder;

    public ResumeController(ResumeService resumeService, UserService userService, FilterBuilder filterBuilder,
                            FilterSpecificationConverter filterSpecificationConverter) {
        this.resumeService = resumeService;
        this.userService = userService;
        this.filterBuilder = filterBuilder;
        this.filterSpecificationConverter = filterSpecificationConverter;
    }

    @PostMapping("/resumes")
    public ResponseEntity<ResponseCreateResumeDTO> createResume(@Valid @RequestBody Resume resume)
            throws IdInvalidException {
        boolean isIdExist = this.resumeService.isExistByUserAndJob(resume);
        if (!isIdExist) {
            throw new IdInvalidException("User or Job is not exist");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.resumeService.createResume(resume));
    }

    @PutMapping("/resumes")
    public ResponseEntity<ResponseUpdateResumeDTO> updateResume(@RequestBody Resume resume) throws IdInvalidException {
        Optional<Resume> currentResume = this.resumeService.getResumeById(resume.getId());
        if (currentResume.isEmpty()) {
            throw new IdInvalidException("Resume is not exist");
        }
        Resume reqResume = currentResume.get();
        reqResume.setStatus(resume.getStatus());
        return ResponseEntity.ok().body(this.resumeService.updateResume(reqResume));
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> currentResume = this.resumeService.getResumeById(id);
        if (currentResume.isEmpty()) {
            throw new IdInvalidException("Resume is not exist");
        }
        this.resumeService.deleteResume(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/resumes/{id}")
    public ResponseEntity<ResponseGetResumeDTO> getResumeById(@PathVariable("id") long id) throws IdInvalidException {
        Optional<Resume> currentResume = this.resumeService.getResumeById(id);
        if (currentResume.isEmpty()) {
            throw new IdInvalidException("Resume is not exist");
        }
        return ResponseEntity.ok().body(this.resumeService.getResumeById(currentResume.get()));
    }

    @GetMapping("/resumes")
    @ApiMessage("Get all resume success")
    public ResponseEntity<ResultPaginationDTO> getAllResume(@Filter Specification<Resume> specification,
                                                            Pageable pageable) {

        return ResponseEntity.ok().body(this.resumeService.getAllResume(specification, pageable));
    }

    @PostMapping("/resumes/by-user")
    @ApiMessage("Get list resume by user")
    public ResponseEntity<ResultPaginationDTO> getResumeByUSer(
            Pageable pageable) {
        return ResponseEntity.ok().body(this.resumeService.fetchResumeByUser(pageable));
    }

}