package com.example_scloud.scloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ScloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScloudApplication.class, args);
	}

}
