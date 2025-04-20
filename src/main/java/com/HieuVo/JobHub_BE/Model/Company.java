package com.HieuVo.JobHub_BE.Model;

import com.HieuVo.JobHub_BE.Util.SecurityUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  id;

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

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Job> jobs;

    @PrePersist
    public void handleBeforeCreate() {
        this.createBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        this.createAt = Instant.now();
    }

    @PreUpdate
    public void handleBeforeUpdate() {
        this.updateAt = Instant.now();
        this.updateBy= SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
    }
}
