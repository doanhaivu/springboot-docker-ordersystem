package com.example.SimpleOrder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SimpleOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleOrderApplication.class, args);
	}

}
