package com.example.sanction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LoanSanctionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanSanctionServiceApplication.class, args);
	}

}
