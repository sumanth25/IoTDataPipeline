package com.iotdatapipeline.simulator.client;

import com.iotdatapipeline.shared.dto.SensorDataDTO;
import com.iotdatapipeline.simulator.kafka.SensorDataProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Simulates the generation of random sensor data for various IoT devices, including thermostats,
 * heart rate monitors, and fuel gauges. The generated data is then sent to the Kafka topic "sensor-topic".
 * This class uses the {@link SensorDataProducer} to create and send the simulated sensor data.
 *
 * <p>Each method is scheduled to run every second (fixed rate) and generates data for a specific IoT device.</p>
 */
@Component
public class IoTDeviceSimulator {

    private final SensorDataProducer sensorDataProducer;

    /**
     * Constructor to initialize the IoTDeviceSimulator with the required dependency.
     * @param sensorDataProducer The service used to generate and send sensor data.
     */
    public IoTDeviceSimulator(SensorDataProducer sensorDataProducer) {
        this.sensorDataProducer = sensorDataProducer;
    }

    /**
     * Simulates sending random temperature data for the thermostat device.
     * This method runs every 1 second and generates random temperature readings.
     * It then sends the generated data to the Kafka topic "sensor-topic".
     */
    @Scheduled(fixedRate = 1000) // Runs every 1 second
    public void simulateThermostat() {
        SensorDataDTO thermostatData = sensorDataProducer.generateRandomSensorData(
                "device_thermostat", "temperature", "°C");
        sensorDataProducer.sendSensorData("sensor-topic", thermostatData);
    }

    /**
     * Simulates sending random heart rate data for the heart rate monitor device.
     * This method runs every 1 second and generates random heart rate readings.
     * It then sends the generated data to the Kafka topic "sensor-topic".
     */
    @Scheduled(fixedRate = 1000) // Runs every 1 second
    public void simulateHeartRateMonitor() {
        SensorDataDTO heartRateData = sensorDataProducer.generateRandomSensorData(
                "device_heart_rate", "heart_rate", "bpm");
        sensorDataProducer.sendSensorData("sensor-topic", heartRateData);
    }

    /**
     * Simulates sending random fuel level data for the fuel gauge device.
     * This method runs every 1 second and generates random fuel level readings.
     * It then sends the generated data to the Kafka topic "sensor-topic".
     */
    @Scheduled(fixedRate = 1000) // Runs every 1 second
    public void simulateFuelGauge() {
        SensorDataDTO fuelData = sensorDataProducer.generateRandomSensorData(
                "device_fuel", "fuel_level", "%");
        sensorDataProducer.sendSensorData("sensor-topic", fuelData);
    }
}

