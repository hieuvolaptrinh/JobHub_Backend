package com.HieuVo.JobHub_BE.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank(message = "Username cannot be blank")
    private String username; // là email á

    @NotBlank(message = "Password cannot be blank")
    private String password;
}
