package com.HieuVo.JobHub_BE.DTO.Response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseLoginDTO {
    private String accessToken;
    private UserLogin user;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserLogin {
        private long id;
        private String email;
        private String name;
    }

}
