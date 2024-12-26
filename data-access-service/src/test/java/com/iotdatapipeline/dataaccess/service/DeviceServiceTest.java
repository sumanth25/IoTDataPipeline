package com.iotdatapipeline.dataaccess.service;

import com.iotdatapipeline.dataaccess.exception.DeviceNotFoundException;
import com.iotdatapipeline.dataaccess.repository.DeviceRepository;
import com.iotdatapipeline.dataaccess.util.DeviceObjectMapperUtil;
import com.iotdatapipeline.shared.dto.DeviceDTO;
import com.iotdatapipeline.shared.model.Device;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private DeviceObjectMapperUtil deviceObjectMapperUtil;

    @InjectMocks
    private DeviceService deviceService;

    private DeviceDTO deviceDTO;
    private Device device;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up a sample device DTO and device object
        deviceDTO = new DeviceDTO();
        deviceDTO.setId("123");
        deviceDTO.setDeviceType("Device1");

        device = new Device();
        device.setId("123");
        device.setDeviceType("Device1");
    }

    @Test
    void testGetDeviceById_DeviceNotFound() {
        // Given
        when(deviceRepository.findById("123")).thenReturn(Optional.empty());

        // When & Then
        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> {
            deviceService.getDeviceById("123");
        });

        assertEquals("Device not found with ID: 123", exception.getMessage());
        verify(deviceRepository, times(1)).findById("123");
    }
}
