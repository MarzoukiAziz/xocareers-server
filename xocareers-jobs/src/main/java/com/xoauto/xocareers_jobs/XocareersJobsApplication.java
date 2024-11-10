package com.xoauto.xocareers_jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class XocareersJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(XocareersJobsApplication.class, args);
	}

}
