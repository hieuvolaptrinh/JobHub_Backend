package com.HieuVo.JobHub_BE.service;

import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.repository.CompanyRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company handCreateCompany(Company company) {
        return this.companyRepository.save(company);
    }
}
