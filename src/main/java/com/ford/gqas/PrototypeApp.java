package com.ford.gqas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class PrototypeApp {

	public static void main(String[] args) {
		SpringApplication.run( PrototypeApp.class, args);
	}
	// this does not work. 	
//	public WebMvcConfigurer corsConfigurer(){		
//		return new WebMvcConfigurerAdapter(){
//			public void addCorsMappings( CorsRegistry registry ){
//				registry.addMapping("/student").allowedOrigins( "http://localhost:8081" );
//			}
//		};
//	}
	
}
