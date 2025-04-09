package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping
    public Company createCompany(@RequestBody Company company) {
        return this.companyService.createCompany(company);
    }

}
