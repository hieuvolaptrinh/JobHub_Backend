package com.HieuVo.JobHub_BE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.HieuVo.JobHub_BE.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    User findByEmail(String email);

    User findByName(String username);
}
