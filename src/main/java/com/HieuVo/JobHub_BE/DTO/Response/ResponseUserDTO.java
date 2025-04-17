package com.HieuVo.JobHub_BE.DTO.Response;


import com.HieuVo.JobHub_BE.Util.Constant.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDTO {
    private long id;
    private String email;
    private String name;
    private GenderEnum gender;
    private String address;
    private int age;
    private Instant updatedAt;
    private Instant createdAt;
    private CompanyUser company;


    public ResponseUserDTO(long id, @NotBlank(message = "Email is required") String email, String name, GenderEnum gender, String address, Integer age, Instant updatedAt, Instant createdAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.age = age;
        this.address = address;
        this.gender = gender;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompanyUser {
        private long id;
        private String name;
    }
}