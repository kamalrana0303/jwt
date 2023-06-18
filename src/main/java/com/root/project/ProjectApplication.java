package com.root.project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="User API", version = "2.0", description = "User microservice"))
public class ProjectApplication extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		System.out.println("*************SpringApplicationBuilder*************************");
		return application.sources(ProjectApplication.class);
	}
	public static void main(String[] args) {
		System.out.println("*************ProjectApplication*************************");
		SpringApplication.run(ProjectApplication.class, args);
	}

}
