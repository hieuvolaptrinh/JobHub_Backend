package com.HieuVo.Employee_Recruitment_Management.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    private String password;

    private String email;
}
