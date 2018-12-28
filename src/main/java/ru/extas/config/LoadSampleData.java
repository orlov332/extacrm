package ru.extas.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadSampleData {

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("Preloading sample data...");

        };
    }
}
