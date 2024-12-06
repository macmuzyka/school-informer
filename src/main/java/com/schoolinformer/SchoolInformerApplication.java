package com.schoolinformer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolInformerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolInformerApplication.class, args);
	}

}
