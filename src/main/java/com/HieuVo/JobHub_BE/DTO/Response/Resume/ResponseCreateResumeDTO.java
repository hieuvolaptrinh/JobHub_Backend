package com.HieuVo.JobHub_BE.DTO.Response.Resume;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResponseCreateResumeDTO {
    private long id;
    private Instant createdAt;
    private String createdBy;
}
