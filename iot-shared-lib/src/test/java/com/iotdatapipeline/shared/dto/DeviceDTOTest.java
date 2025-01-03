package com.iotdatapipeline.shared.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DeviceDTOTest {

    private DeviceDTO deviceDTO;

    @BeforeEach
    public void setup() {
        // Initialize the DeviceDTO object with values
        deviceDTO = new DeviceDTO("device_1", "sensor",1735188696);
    }

    @Test
    public void testGettersAndSetters() {
        // Verify getter methods
        assertEquals("device_1", deviceDTO.getId(), "ID should be device_1");
        assertEquals("sensor", deviceDTO.getDeviceType(), "Device type should be sensor");

        // Modify values using setters
        deviceDTO.setId("device_2");
        deviceDTO.setDeviceType("thermostat");

        // Verify setter methods
        assertEquals("device_2", deviceDTO.getId(), "ID should be device_2");
        assertEquals("thermostat", deviceDTO.getDeviceType(), "Device type should be thermostat");
    }

    @Test
    public void testConstructor() {
        // Create a new instance using the constructor
        DeviceDTO newDeviceDTO = new DeviceDTO("device_3", "lightbulb",1735188696);

        // Verify the constructor values
        assertEquals("device_3", newDeviceDTO.getId(), "ID should be device_3");
        assertEquals("lightbulb", newDeviceDTO.getDeviceType(), "Device type should be lightbulb");
    }

    @Test
    public void testToString() {
        // Expected toString format
        String expectedString = "DeviceDTO(id=device_1, deviceType=sensor, createdAt=1735188696)";

        // Verify the toString method generated by Lombok
        assertEquals(expectedString, deviceDTO.toString(), "toString() should match the expected format");
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two DeviceDTO objects with the same values
        DeviceDTO deviceDTO1 = new DeviceDTO("device_1", "sensor",1735188696);
        DeviceDTO deviceDTO2 = new DeviceDTO("device_1", "sensor",1735188696);

        // Verify that the objects are equal and have the same hashCode
        assertEquals(deviceDTO1, deviceDTO2, "Objects with same values should be equal");
        assertEquals(deviceDTO1.hashCode(), deviceDTO2.hashCode(), "Objects with same values should have the same hashCode");

        // Modify one property to make the objects different
        deviceDTO2.setDeviceType("thermostat");

        // Verify they are not equal after modification
        assertNotEquals(deviceDTO1, deviceDTO2, "Objects should not be equal after modifying a property");
    }
}
