package com.HieuVo.JobHub_BE.DTO.Response.Resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResponseUpdateResumeDTO {
    private String updatedBy;
    private Instant updatedAt;
}
