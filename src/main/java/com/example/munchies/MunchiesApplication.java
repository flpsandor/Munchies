package com.example.munchies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ServletComponentScan
public class MunchiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(MunchiesApplication.class, args);
    }
}
