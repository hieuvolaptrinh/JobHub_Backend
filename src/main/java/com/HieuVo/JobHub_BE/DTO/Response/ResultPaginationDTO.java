package com.HieuVo.JobHub_BE.DTO.Response;

import com.HieuVo.JobHub_BE.DTO.Meta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultPaginationDTO {
private Meta meta;
private Object result;
}
