package com.HieuVo.JobHub_BE.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String logo;

    private String address;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String description;

    private String updateBy;

    private String createBy;

    private Instant createAt;

    private Instant updateAt;
}
