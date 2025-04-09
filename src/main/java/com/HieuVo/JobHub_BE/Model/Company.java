package com.HieuVo.JobHub_BE.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Name is not blank")
    private String name;

    @NotBlank(message = "logo is not blank")
    private String logo;

    @NotBlank(message = "address is not blank")
    private String address;

    @Column(columnDefinition = "VARCHAR(MAX)")
    private String description;

    private String updateBy;

    private String createBy;

    private Instant createAt;

    private Instant updateAt;

    @PrePersist
    public void handleBeforeCreate() {
        this.createBy = "Hiếu võ ";
        this.createAt = Instant.now();
        this.updateBy = "Hiếu võ ";
    }
}
