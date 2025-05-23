package com.HieuVo.JobHub_BE.repository;

import com.HieuVo.JobHub_BE.Model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    Page<Resume> findAll(Specification<Resume> specification, Pageable pageable);
}
