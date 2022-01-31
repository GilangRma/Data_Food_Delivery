package com.dimata.demo.user.demo_data_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class DemoDataUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoDataUserApplication.class, args);
	}

}
