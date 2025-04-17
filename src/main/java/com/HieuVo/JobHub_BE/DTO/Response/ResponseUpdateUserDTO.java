package com.HieuVo.JobHub_BE.DTO.Response;

import com.HieuVo.JobHub_BE.Util.Constant.GenderEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResponseUpdateUserDTO {
    private long id;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;

    private Instant updatedAt;
    private CompanyUser company;


    @Getter
    @Setter
    public static class CompanyUser {
        private long id;
        private String name;
    }

}
