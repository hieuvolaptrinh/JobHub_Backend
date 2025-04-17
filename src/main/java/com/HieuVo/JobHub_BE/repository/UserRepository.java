package com.HieuVo.JobHub_BE.repository;

import com.HieuVo.JobHub_BE.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.HieuVo.JobHub_BE.Model.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    User findByName(String username);
    boolean existsByEmail(String email);

    User findByRefreshTokenAndEmail(String refresh_token, String email);
    List<User> findByCompany(Company company);
}
