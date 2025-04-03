package com.HieuVo.Employee_Recruitment_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//disable security

@SpringBootApplication
//		(exclude =
//		{org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
//		})
public class EmployeeRecruitmentManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeRecruitmentManagementApplication.class, args);
	}

}
