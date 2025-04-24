package com.HieuVo.JobHub_BE.repository;


import com.HieuVo.JobHub_BE.Model.Subscriber;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long>, JpaSpecificationExecutor<Subscriber> {
    Subscriber findByEmail(@NotBlank(message = "Skill name cannot be blank!") String email);

    boolean existsByEmail(@NotBlank(message = "Skill name cannot be blank!") String email);
}
