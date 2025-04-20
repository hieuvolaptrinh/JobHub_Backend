package com.HieuVo.JobHub_BE.DTO.Response.Resume;


import com.HieuVo.JobHub_BE.Util.Constant.ResumeStateEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResponseGetResumeDTO {
    private long id;
    private String email;
    private String url;
    private ResumeStateEnum status;

    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;

    private String companyName;
    private UserResume user;
    private JobResume job;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class UserResume {
        private long id;
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class JobResume {
        private long id;
        private String name;
    }
}