package com.example.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoInitializer implements CommandLineRunner {

    @Value("${greeting}")
    private String greeting;

    @Override
    public void run(String... args) {
        LoggerFactory.getLogger(getClass()).info("Initializing using greeting '{}'...", greeting);
    }
}
