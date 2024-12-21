package com.application.api.installment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class InstallmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstallmentApplication.class, args);
	}

}
