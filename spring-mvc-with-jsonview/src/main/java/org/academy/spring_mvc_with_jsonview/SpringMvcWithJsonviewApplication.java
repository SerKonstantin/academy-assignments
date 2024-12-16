package org.academy.spring_mvc_with_jsonview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringMvcWithJsonviewApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcWithJsonviewApplication.class, args);
	}

}
