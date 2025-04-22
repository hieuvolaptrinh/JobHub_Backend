package com.HieuVo.JobHub_BE.repository;

import com.HieuVo.JobHub_BE.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

    boolean existsByName(String name);
}
