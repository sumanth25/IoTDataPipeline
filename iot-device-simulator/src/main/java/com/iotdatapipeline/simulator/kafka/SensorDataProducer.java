package com.iotdatapipeline.simulator.kafka;

import com.iotdatapipeline.shared.dto.SensorDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class SensorDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(SensorDataProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    // Constructor injection of KafkaTemplate
    @Autowired
    public SensorDataProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    // Send Sensor Data to Kafka asynchronously and handle success/failure
    public void sendSensorData(String topic, SensorDataDTO data) {
        // Create the JSON message
        String jsonData = String.format("{ \"deviceId\": \"%s\", \"sensorType\": \"%s\", \"value\": %.2f, \"unit\": \"%s\", \"timestamp\": %d }",
                data.getDeviceId(), data.getSensorType(), data.getValue(), data.getUnit(), data.getTimestamp());

        // Send message asynchronously using CompletableFuture
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, data.getDeviceId(), jsonData);

        // Handle success or failure using CompletableFuture's methods
        future.thenAccept(result -> {
            // Handle success
            logger.info("Successfully produced message: {}", jsonData);
        }).exceptionally(ex -> {
            // Handle failure
            logger.error("Error producing message: {}", ex.getMessage(), ex);
            return null; // Return null to satisfy the exceptionally method signature
        });
    }

    // Simulate data for different devices
    public SensorDataDTO generateRandomSensorData(String deviceId, String sensorType, String unit) {
        Random random = new Random();
        SensorDataDTO data = new SensorDataDTO();
        data.setDeviceId(deviceId);
        data.setSensorType(sensorType);
        data.setValue(random.nextDouble() * 100); // Random value between 0 and 100
        data.setUnit(unit);
        data.setTimestamp(System.currentTimeMillis());
        return data;
    }
}
