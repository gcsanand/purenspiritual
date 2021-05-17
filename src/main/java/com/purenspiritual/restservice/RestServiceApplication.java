package com.purenspiritual.restservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan({"com.purenspiritual.repository", "com.purenspiritual.restservice.controller", 
//				"com.purenspiritual.model", "com.purenspiritual.restservice.exception"})

//@ComponentScan({"com.purenspiritual.repository"})

public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

}