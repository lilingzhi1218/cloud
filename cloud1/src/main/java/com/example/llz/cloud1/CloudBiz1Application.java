package com.example.llz.cloud1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class CloudBiz1Application {

    public static void main(String[] args) {
        SpringApplication.run(CloudBiz1Application.class, args);
    }

}
