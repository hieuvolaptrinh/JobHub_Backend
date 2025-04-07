package com.HieuVo.JobHub_BE.DTO;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int status;
    private String error;
    private Object message;
    private T data;

}
