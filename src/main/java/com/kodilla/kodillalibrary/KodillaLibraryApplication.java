package com.kodilla.kodillalibrary;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition
@SpringBootApplication
public class KodillaLibraryApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KodillaLibraryApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KodillaLibraryApplication.class);
	}
}
