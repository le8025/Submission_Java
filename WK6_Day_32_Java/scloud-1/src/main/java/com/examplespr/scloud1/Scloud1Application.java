package com.examplespr.scloud1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Scloud1Application {

	public static void main(String[] args) {
		SpringApplication.run(Scloud1Application.class, args);
	}

}
