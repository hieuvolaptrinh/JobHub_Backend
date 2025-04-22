package com.HieuVo.JobHub_BE.repository;

import com.HieuVo.JobHub_BE.Model.Permission;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission> {

    boolean existsByModuleAndApiPathAndMethod(String module,  String apiPath, String method);
   List<Permission> findByIdIn(List<Long> id);

}
