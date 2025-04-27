package com.HieuVo.JobHub_BE;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableAsync
@EnableScheduling
public class JobHubBE {

	public static void main(String[] args) {
		SpringApplication.run(JobHubBE.class, args);
	}

}
