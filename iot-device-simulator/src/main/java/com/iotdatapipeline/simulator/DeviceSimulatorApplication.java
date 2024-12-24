package com.iotdatapipeline.simulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * The main entry point for the Device Simulator application.
 * This class is responsible for bootstrapping the Spring Boot application.
 *
 * <p>The {@link SpringApplication#run(Class, String...)} method is invoked to launch the application.</p>
 * It initiates the application context and starts the Spring Boot application with all the configured beans,
 * components, and services.
 *
 * <p>This application simulates IoT device data generation and processing using Spring Boot and Kafka.</p>
 *
 * <p>The {@link EnableScheduling} annotation enables the scheduling of tasks, allowing periodic tasks like
 * simulating device data to run at fixed intervals.</p>
 */
@SpringBootApplication
@EnableScheduling
public class DeviceSimulatorApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(DeviceSimulatorApplication.class, args);
    }
}
