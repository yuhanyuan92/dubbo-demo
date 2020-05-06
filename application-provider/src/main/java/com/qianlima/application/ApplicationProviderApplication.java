package com.qianlima.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ApplicationProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationProviderApplication.class,args);
    }
}
