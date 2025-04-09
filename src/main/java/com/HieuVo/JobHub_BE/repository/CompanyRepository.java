package com.HieuVo.JobHub_BE.repository;

import com.HieuVo.JobHub_BE.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
