package com.HieuVo.JobHub_BE.controller;


import com.HieuVo.JobHub_BE.DTO.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Model.Company;
import com.HieuVo.JobHub_BE.service.CompanyService;
import jakarta.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<Company> createCompany(@RequestBody @Valid Company company ) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleCreateCompany(company));
    }

    @GetMapping()
    public ResponseEntity<ResultPaginationDTO> getAllCompanies(@RequestParam("current") Optional<String> current,
                                                               @RequestParam("pageSize" ) Optional<String> size
                        ){
        String currentPage = current.isPresent() ? current.get() : "1";
        String pageSize = size.orElse("4");
        Pageable pageable = PageRequest.of(Integer.parseInt(currentPage)-1, Integer.parseInt(pageSize));
        return ResponseEntity.ok(this.companyService.fetchAllCompany(pageable));
    }

    @PutMapping
    public ResponseEntity<Company> updateCompany(  @Valid @RequestBody Company company) {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.handleUpDateCompany(company));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable int id) {
        this.companyService.handleDeleteCompany(id);
        return null;
    }


}
