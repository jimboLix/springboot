package com.jimbolix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
//		SpringApplication springApplication = new SpringApplication();
//		Set<String> sources = new HashSet<>();
//		sources.add(SpringbootApplication.class.getName());
//		springApplication.setSources(sources);
//		ConfigurableApplicationContext context = springApplication.run(args);
//		SpringbootApplication bean = context.getBean(SpringbootApplication.class);
	}
}
