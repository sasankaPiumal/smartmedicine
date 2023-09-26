package com.nibm.smartmedicine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SmartmedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartmedicineApplication.class, args);
    }

}
