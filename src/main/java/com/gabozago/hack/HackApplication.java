package com.gabozago.hack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HackApplication {

	public static final String APPLICATION_LOCATIONS = "spring.config.additional-location="
			+ "classpath:application.properties,"
			+ "optional:aws.yml";
	public static void main(String[] args) {
//		SpringApplication.run(HackApplication.class, args);
		new SpringApplicationBuilder(HackApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}

}
