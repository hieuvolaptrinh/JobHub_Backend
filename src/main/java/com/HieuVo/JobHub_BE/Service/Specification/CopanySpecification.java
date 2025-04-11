package com.HieuVo.JobHub_BE.Service.Specification;

import com.HieuVo.JobHub_BE.Model.Company;
import org.springframework.data.jpa.domain.Specification;
public class CopanySpecification {
    public static Specification<Company> nameLike(String name){
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("name"), "%" + name + "%");
        };
    }
}
