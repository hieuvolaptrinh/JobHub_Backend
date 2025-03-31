package com.HieuVo.Employee_Recruitment_Management.controller;

import com.HieuVo.Employee_Recruitment_Management.service.Error.IdInvalidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControler {

    @GetMapping()
    public String test() {
        return "Hello World";
    }
}
