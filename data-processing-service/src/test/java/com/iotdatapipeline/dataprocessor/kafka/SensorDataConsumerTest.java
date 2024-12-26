package com.iotdatapipeline.dataprocessor.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iotdatapipeline.dataprocessor.exception.DataProcessingException;
import com.iotdatapipeline.dataprocessor.service.ProcessingDataService;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SensorDataConsumerTest {

    @InjectMocks
    private SensorDataConsumer sensorDataConsumer;

    @Mock
    private ProcessingDataService processingDataService;

    @BeforeEach
    public void setup() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testConsume_ValidJson() throws JsonProcessingException {
        // Valid JSON input
        String validJson = "{\"deviceId\": \"device123\", \"sensorType\": \"temperature\", \"value\": 25.6}";

        // Expected DTO object after parsing
        SensorDataDTO expectedData = new SensorDataDTO("device123", "temperature", 25.6, "Celsius", 1672531199);

        // Act: Call the method to parse the JSON
        SensorDataDTO actualData = sensorDataConsumer.parseSensorData(validJson);

        // Assert: Verify that the parsed data matches the expected result
        assertNotNull(actualData);
        assertEquals(expectedData.getDeviceId(), actualData.getDeviceId());
        assertEquals(expectedData.getSensorType(), actualData.getSensorType());
        assertEquals(expectedData.getValue(), actualData.getValue());
    }

    @Test
    public void testConsume_InvalidJson() {
        // Invalid JSON (missing value field)
        String invalidJson = "{invalid json}";

        // Act & Assert: Expect KafkaProcessingException to be thrown when invalid JSON is passed
        assertThrows(DataProcessingException.class, () -> {
            sensorDataConsumer.consume(invalidJson);
        });
    }

    @Test
    public void testConsume_ValidJson_StoresData() throws JsonProcessingException {
        // Valid JSON input
        String validJson = "{\"deviceId\": \"device123\", \"sensorType\": \"temperature\", \"value\": 25.6}";

        // Mock the behavior of processingDataService.saveSensorData() (no-op, just avoid exceptions)
        doNothing().when(processingDataService).saveSensorData(any(SensorDataDTO.class));

        // Act: Simulate the consume method (this will call saveSensorData internally)
        sensorDataConsumer.consume(validJson);

        // Assert: Verify that saveSensorData was called with the expected data
        verify(processingDataService, times(1)).saveSensorData(any(SensorDataDTO.class));
    }
}
