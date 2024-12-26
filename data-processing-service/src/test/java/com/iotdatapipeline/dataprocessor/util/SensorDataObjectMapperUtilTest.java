package com.iotdatapipeline.dataprocessor.util;

import com.iotdatapipeline.shared.dto.SensorDataDTO;
import com.iotdatapipeline.shared.model.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorDataObjectMapperUtilTest {

    private SensorDataObjectMapperUtil sensorDataObjectMapperUtil;
    private SensorData sensorData;
    private SensorDataDTO sensorDataDTO;

    @BeforeEach
    void setUp() {
        // Initialize the utility class
        sensorDataObjectMapperUtil = new SensorDataObjectMapperUtil();

        // Set up test data for SensorData and SensorDataDTO
        sensorData = new SensorData();
        sensorData.setDeviceId("12345");
        sensorData.setSensorType("temperature");
        sensorData.setValue(23.5);
        sensorData.setUnit("bpm");
        sensorData.setTimestamp(1735188696);

        sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setDeviceId("12345");
        sensorDataDTO.setSensorType("temperature");
        sensorDataDTO.setValue(23.5);
        sensorDataDTO.setUnit("bpm");
        sensorDataDTO.setTimestamp(1735188696);
    }

    @Test
    void testMapToSensorData() {
        // Act
        SensorData mappedSensorData = sensorDataObjectMapperUtil.mapToSensorData(sensorDataDTO);

        // Assert
        assertNotNull(mappedSensorData);
        assertEquals(sensorDataDTO.getDeviceId(), mappedSensorData.getDeviceId());
        assertEquals(sensorDataDTO.getSensorType(), mappedSensorData.getSensorType());
        assertEquals(sensorDataDTO.getValue(), mappedSensorData.getValue());
        assertEquals(sensorDataDTO.getUnit(), mappedSensorData.getUnit());
        assertEquals(sensorDataDTO.getTimestamp(), mappedSensorData.getTimestamp());
    }

    @Test
    void testObjectToJson() throws Exception {
        // Act
        String json = sensorDataObjectMapperUtil.objectToJson(sensorData);

        // Assert
        assertNotNull(json);
        assertTrue(json.contains("deviceId"));
        assertTrue(json.contains("sensorType"));
        assertTrue(json.contains("value"));
        assertTrue(json.contains("unit"));
        assertTrue(json.contains("timestamp"));
    }

    @Test
    void testJsonToObject() throws Exception {
        // Arrange
        String json = sensorDataObjectMapperUtil.objectToJson(sensorData);

        // Act
        SensorData parsedSensorData = sensorDataObjectMapperUtil.jsonToObject(json, SensorData.class);

        // Assert
        assertNotNull(parsedSensorData);
        assertEquals(sensorData.getDeviceId(), parsedSensorData.getDeviceId());
        assertEquals(sensorData.getSensorType(), parsedSensorData.getSensorType());
        assertEquals(sensorData.getValue(), parsedSensorData.getValue());
        assertEquals(sensorData.getUnit(), parsedSensorData.getUnit());
        assertEquals(sensorData.getTimestamp(), parsedSensorData.getTimestamp());
    }

    @Test
    void testJsonToObject_InvalidJson() {
        // Arrange
        String invalidJson = "invalid json string";

        // Act & Assert
        assertThrows(Exception.class, () -> sensorDataObjectMapperUtil.jsonToObject(invalidJson, SensorData.class));
    }
}
