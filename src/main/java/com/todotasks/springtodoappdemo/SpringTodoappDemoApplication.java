package com.todotasks.springtodoappdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringTodoappDemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringTodoappDemoApplication.class, args);
	}
}
