package ru.extas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
//@ComponentScan(basePackages = "ru.extas")
public class ApplicationEntry {
    public static void main(final String[] args) {
        SpringApplication.run(ApplicationEntry.class, args);
    }
}