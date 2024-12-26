package com.iotdatapipeline.dataprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point for the Spring Boot application.
 * <p>
 * This class is marked as a Spring Boot application using the {@link SpringBootApplication} annotation,
 * which automatically configures the Spring application context and starts the application.
 * It contains the {@link #main(String[])} method, which launches the application.
 * </p>
 */
@SpringBootApplication
public class DataProcessingApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(DataProcessingApplication.class, args);
    }
}
