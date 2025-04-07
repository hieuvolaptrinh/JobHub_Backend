package com.HieuVo.Employee_Recruitment_Management.DTO;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int status;
    private String error;
    private Object message;
    private T data;

}
