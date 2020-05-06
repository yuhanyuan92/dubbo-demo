package com.qianlima.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication(
        exclude = FeignAutoConfiguration.class
)
@EnableDiscoveryClient
public class ApplicationWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationWebApplication.class, args);
    }
}
