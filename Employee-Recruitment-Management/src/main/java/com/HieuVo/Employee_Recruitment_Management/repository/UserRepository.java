package com.HieuVo.Employee_Recruitment_Management.repository;

import com.HieuVo.Employee_Recruitment_Management.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findByUsername(String username);
}
