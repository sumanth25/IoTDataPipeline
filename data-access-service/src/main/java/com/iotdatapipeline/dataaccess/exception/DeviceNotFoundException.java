package com.iotdatapipeline.dataaccess.exception;

public class DeviceNotFoundException extends RuntimeException {

    public DeviceNotFoundException(String deviceId) {
        super("Device not found with ID: " + deviceId);
    }
}

