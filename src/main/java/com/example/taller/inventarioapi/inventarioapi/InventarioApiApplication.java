package com.example.taller.inventarioapi.inventarioapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = "com.example.taller.inventarioapi"
)
public class InventarioApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventarioApiApplication.class, args);
    }
}