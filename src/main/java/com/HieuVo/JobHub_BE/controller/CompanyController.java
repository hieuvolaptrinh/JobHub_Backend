package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.DTO.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Service.CompanyService;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    @ApiMessage("create a company success")
    public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleCreateCompany(company));
    }

    @GetMapping()
    @ApiMessage("get all company success")
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(
            @Filter Specification<Company> spec,
            Pageable pageable // page,size,sort
             ){
        return ResponseEntity.ok(this.companyService.fetchAllCompany(spec,pageable));
    }

    @PutMapping
    @ApiMessage("update company success")
    public ResponseEntity<Company> updateCompany(  @Valid @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleUpDateCompany(company));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete company success")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        this.companyService.handleDeleteCompany(id);
        return null;
    }


}
