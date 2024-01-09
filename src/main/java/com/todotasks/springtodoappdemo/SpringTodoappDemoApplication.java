package com.todotasks.springtodoappdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class SpringTodoappDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringTodoappDemoApplication.class, args);
		System.out.println("Running");
	}

}
