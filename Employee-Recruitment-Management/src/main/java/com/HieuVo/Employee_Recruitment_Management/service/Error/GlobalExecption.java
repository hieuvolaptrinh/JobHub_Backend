package com.HieuVo.Employee_Recruitment_Management.service.Error;

import com.HieuVo.Employee_Recruitment_Management.Model.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExecption {
    @ExceptionHandler(value = {IdInvalidException.class})
    public ResponseEntity<RestResponse<Object>> handleIdInvalidException(IdInvalidException idException) {
        RestResponse<Object> restResponse = new RestResponse<Object>();
        restResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        restResponse.setError(idException.getMessage());
        restResponse.setMessage("id invalidexeception");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(restResponse);
    }
}
