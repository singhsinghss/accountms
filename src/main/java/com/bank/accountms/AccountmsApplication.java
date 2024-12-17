package com.bank.accountms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.bank.accountms")
@EnableJpaRepositories(basePackages = "com.bank.accountms.Repositories")
public class AccountmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountmsApplication.class, args);
	}

}
