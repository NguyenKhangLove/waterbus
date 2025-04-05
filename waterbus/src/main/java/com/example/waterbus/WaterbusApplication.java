package com.example.waterbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WaterbusApplication {
    public static void main(String[] args) {
        System.out.println("App started");
        SpringApplication.run(WaterbusApplication.class, args);
    }
}
