package com.iotdatapipeline.dataaccess.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotdatapipeline.shared.dto.DeviceDTO;
import com.iotdatapipeline.shared.model.Device;

public class DeviceObjectMapperUtil {

    private static final ObjectMapper objectMapper= new ObjectMapper();

    // Method to map from Device to DeviceDTO
    public static DeviceDTO mapToDeviceDTO(Device device) {
        try {
            // Convert the Device document to a DeviceDTO
            return objectMapper.convertValue(device, DeviceDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping Device to DeviceDTO", e);
        }
    }

    // Method to map from DeviceDTO to Device (if needed)
    public static Device mapToDevice(DeviceDTO deviceDTO) {
        try {
            // Convert the DeviceDTO to a Device document
            return objectMapper.convertValue(deviceDTO, Device.class);
        } catch (Exception e) {
            throw new RuntimeException("Error mapping DeviceDTO to Device", e);
        }
    }
}

