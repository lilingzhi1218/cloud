package com.example.llz.cloud2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.example.llz.cloud2.feign")
public class Cloud2Application {

	public static void main(String[] args) {
		SpringApplication.run(Cloud2Application.class, args);
	}

}
