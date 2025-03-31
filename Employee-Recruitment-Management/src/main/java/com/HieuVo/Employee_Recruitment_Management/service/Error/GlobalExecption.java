package com.HieuVo.Employee_Recruitment_Management.service.Error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExecption {
    @ExceptionHandler(value = {IdInvalidException.class})
    public ResponseEntity<String> handleIdInvalidException(IdInvalidException idException) {
        return ResponseEntity.badRequest().body(idException.getMessage());
    }
}
