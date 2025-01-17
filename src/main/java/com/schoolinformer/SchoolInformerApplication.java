package com.schoolinformer;

import com.schoolinformer.config.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableConfigurationProperties({ApplicationConfig.class})
@EnableJpaAuditing
public class SchoolInformerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolInformerApplication.class, args);
    }

}
