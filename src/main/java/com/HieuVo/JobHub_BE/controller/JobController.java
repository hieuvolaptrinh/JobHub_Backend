package com.HieuVo.JobHub_BE.controller;

import com.HieuVo.JobHub_BE.DTO.Response.Job.ResponseCreateJobDTO;
import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.HieuVo.JobHub_BE.Model.Job;
import com.HieuVo.JobHub_BE.Service.JobService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;



@RestController
@RequestMapping("/api/v1")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    @ApiMessage("Create new job successed")
    public ResponseEntity<ResponseCreateJobDTO> createJob(@RequestBody Job job) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.jobService.createJob(job));
    }

    @PutMapping("/jobs")
    @ApiMessage("Update new job successed")
    public ResponseEntity<ResponseCreateJobDTO> updateJobs(@RequestBody Job job) throws IdInvalidException {
        Job currentJob = this.jobService.getJobById(job.getId());
        if (currentJob == null) {
            throw new IdInvalidException("Job not found");
        }
        return ResponseEntity.ok().body(this.jobService.updateJob(job));
    }

    @DeleteMapping("/jobs/{id}")
    @ApiMessage("Delete new job successed")
    public ResponseEntity<Void> deleteJob(@PathVariable("id") long id) throws IdInvalidException {
        Job currentJob = this.jobService.getJobById(id);
        if (currentJob == null) {
            throw new IdInvalidException("Job not found");
        }
        this.jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
//
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") long id) {
        Job currentJob = this.jobService.getJobById(id);
        return ResponseEntity.status(HttpStatus.OK).body(currentJob);
    }

    @GetMapping("/jobs")
    @ApiMessage("Get all Jobs new job successed")
    public ResponseEntity<ResultPaginationDTO> getAllJobs(@Filter Specification<Job> specification,
                                                          Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.jobService.getAllJobs(specification, pageable));
    }

}
