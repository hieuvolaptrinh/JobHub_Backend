package com.HieuVo.JobHub_BE.DTO.Response.Job;

import java.time.Instant;
import java.util.List;

import com.HieuVo.JobHub_BE.Util.Constant.LevelEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreateJobDTO {
    private long id;
    private String name;
    private String location;
    private double salary;
    private int quantity;
    private LevelEnum level;
    private Instant startDate;
    private Instant endDate;
    private boolean isActive;
    private List<String> skill;
    private Instant createdAt;
    private String createdBy;
    private Instant updatedAt;
    private String updateBy;
}
