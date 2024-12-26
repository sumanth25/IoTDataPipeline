package com.iotdatapipeline.dataprocessor.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iotdatapipeline.dataprocessor.exception.DataProcessingException;
import com.iotdatapipeline.dataprocessor.service.ProcessingDataService;
import com.iotdatapipeline.dataprocessor.util.SensorDataObjectMapperUtil;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SensorDataConsumerTest {

    @Mock
    private ProcessingDataService processingDataService;

    @Mock
    private SensorDataObjectMapperUtil sensorDataObjectMapperUtil;

    @InjectMocks
    private SensorDataConsumer sensorDataConsumer;

    private static final Logger logger = LoggerFactory.getLogger(SensorDataConsumerTest.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks and inject them into the consumer
    }

    @Test
    void testConsume_success() throws JsonProcessingException {
        // Arrange
        String recordValue = "{\"id\":\"123\", \"value\":25.5}";
        SensorDataDTO sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setDeviceId("123");
        sensorDataDTO.setValue(25.5);

        // Mock the JSON parsing
        when(sensorDataObjectMapperUtil.jsonToObject(recordValue, SensorDataDTO.class)).thenReturn(sensorDataDTO);

        // Act
        sensorDataConsumer.consume(recordValue);

        // Assert
        ArgumentCaptor<SensorDataDTO> captor = ArgumentCaptor.forClass(SensorDataDTO.class);
        verify(processingDataService).saveSensorData(captor.capture());
        SensorDataDTO savedSensorData = captor.getValue();
        assertEquals(sensorDataDTO.getDeviceId(), savedSensorData.getDeviceId());
        assertEquals(sensorDataDTO.getValue(), savedSensorData.getValue());
    }

    @Test
    void testConsume_failure_dueToJsonProcessingException() throws JsonProcessingException {
        // Arrange
        String recordValue = "{\"id\":\"123\", \"value\":25.5}";

        // Mock the JSON parsing to throw an exception
        when(sensorDataObjectMapperUtil.jsonToObject(recordValue, SensorDataDTO.class))
                .thenThrow(new JsonProcessingException("Invalid JSON") {});

        // Act & Assert
        DataProcessingException exception = assertThrows(DataProcessingException.class, () -> {
            sensorDataConsumer.consume(recordValue);
        });

        assertTrue(exception.getMessage().contains("Error parsing JSON message"));
    }
}
