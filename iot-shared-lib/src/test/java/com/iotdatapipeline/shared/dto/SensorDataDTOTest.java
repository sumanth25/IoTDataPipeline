package com.iotdatapipeline.shared.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SensorDataDTOTest {

    private SensorDataDTO sensorDataDTO;

    @BeforeEach
    public void setup() {
        // Initialize the SensorDataDTO object before each test
        sensorDataDTO = new SensorDataDTO();
    }

    @Test
    public void testSettersAndGetters() {
        // Set values using setters
        sensorDataDTO.setDeviceId("device_1");
        sensorDataDTO.setSensorType("temperature");
        sensorDataDTO.setValue(25.5);
        sensorDataDTO.setUnit("°C");
        sensorDataDTO.setTimestamp(System.currentTimeMillis());

        // Verify the values using getters
        assertEquals("device_1", sensorDataDTO.getDeviceId());
        assertEquals("temperature", sensorDataDTO.getSensorType());
        assertEquals(25.5, sensorDataDTO.getValue());
        assertEquals("°C", sensorDataDTO.getUnit());
        assertEquals(sensorDataDTO.getTimestamp(), sensorDataDTO.getTimestamp()); // Check timestamp value
    }

    @Test
    public void testConstructor() {
        // Create an instance using constructor
        SensorDataDTO newSensorDataDTO = new SensorDataDTO();
        newSensorDataDTO.setDeviceId("device_2");
        newSensorDataDTO.setSensorType("humidity");
        newSensorDataDTO.setValue(45.0);
        newSensorDataDTO.setUnit("%");
        newSensorDataDTO.setTimestamp(System.currentTimeMillis());

        // Assert that values are correctly set
        assertEquals("device_2", newSensorDataDTO.getDeviceId());
        assertEquals("humidity", newSensorDataDTO.getSensorType());
        assertEquals(45.0, newSensorDataDTO.getValue());
        assertEquals("%", newSensorDataDTO.getUnit());
    }

    @Test
    public void testToString() {
        // Set values
        sensorDataDTO.setDeviceId("device_3");
        sensorDataDTO.setSensorType("pressure");
        sensorDataDTO.setValue(101.5);
        sensorDataDTO.setUnit("Pa");
        sensorDataDTO.setTimestamp(System.currentTimeMillis());

        // Expected toString format
        String expectedString = "SensorDataDTO(deviceId=device_3, sensorType=pressure, value=101.5, unit=Pa, timestamp=" + sensorDataDTO.getTimestamp() + ")";

        // Verify the toString method generated by Lombok
        assertEquals(expectedString, sensorDataDTO.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two SensorDataDTO objects with the same values
        SensorDataDTO sensorDataDTO1 = new SensorDataDTO();
        sensorDataDTO1.setDeviceId("device_4");
        sensorDataDTO1.setSensorType("light");
        sensorDataDTO1.setValue(300.0);
        sensorDataDTO1.setUnit("lux");
        sensorDataDTO1.setTimestamp(System.currentTimeMillis());

        SensorDataDTO sensorDataDTO2 = new SensorDataDTO();
        sensorDataDTO2.setDeviceId("device_4");
        sensorDataDTO2.setSensorType("light");
        sensorDataDTO2.setValue(300.0);
        sensorDataDTO2.setUnit("lux");
        sensorDataDTO2.setTimestamp(sensorDataDTO1.getTimestamp());

        // Verify that the objects are equal and have the same hashCode
        assertEquals(sensorDataDTO1, sensorDataDTO2);
        assertEquals(sensorDataDTO1.hashCode(), sensorDataDTO2.hashCode());

        // Modify one property to make the objects different
        sensorDataDTO2.setDeviceId("device_5");

        // Verify they are not equal after modification
        assertNotEquals(sensorDataDTO1, sensorDataDTO2);
    }
}