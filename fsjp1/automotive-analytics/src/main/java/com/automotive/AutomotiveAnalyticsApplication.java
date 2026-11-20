package com.automotive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Automotive Data Analytics Platform.
 * 
 * @SpringBootApplication enables:
 *   - @Configuration: registers this class as a Spring config source
 *   - @EnableAutoConfiguration: auto-configures Spring beans
 *   - @ComponentScan: scans all sub-packages for components
 */
@SpringBootApplication
public class AutomotiveAnalyticsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutomotiveAnalyticsApplication.class, args);
    }
}
