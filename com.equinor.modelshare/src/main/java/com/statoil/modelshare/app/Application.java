package com.equinor.modelshare.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@SpringBootApplication
@EnableConfigurationProperties
@Order(Ordered.LOWEST_PRECEDENCE - 50) // ensure this takes precedence over the Actuator (-10)
public class Application  {
	
	public static void main(String[] args) {		
		SpringApplication.run(Application.class, args);
	}

}

