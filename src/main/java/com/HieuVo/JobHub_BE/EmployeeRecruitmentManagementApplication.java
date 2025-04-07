package com.HieuVo.JobHub_BE;

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
