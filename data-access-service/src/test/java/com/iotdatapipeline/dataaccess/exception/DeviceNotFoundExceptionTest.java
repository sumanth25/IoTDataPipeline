package com.iotdatapipeline.dataaccess.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for DeviceNotFoundException.
 */
public class DeviceNotFoundExceptionTest {

    /**
     * Test that the exception is thrown with the correct message.
     */
    @Test
    public void testDeviceNotFoundExceptionMessage() {
        // Arrange: Define a device ID that will be used in the exception
        String deviceId = "12345";

        // Act: Create the exception
        DeviceNotFoundException exception = new DeviceNotFoundException(deviceId);

        // Assert: Verify that the exception message is correct
        assertEquals("Device not found with ID: " + deviceId, exception.getMessage());
    }

    /**
     * Test that the exception can be instantiated without any issues.
     */
    @Test
    public void testDeviceNotFoundExceptionInstantiation() {
        // Arrange
        String deviceId = "12345";

        // Act & Assert: Ensure the exception can be instantiated without errors
        assertDoesNotThrow(() -> new DeviceNotFoundException(deviceId));
    }
}

