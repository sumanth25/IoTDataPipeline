package com.iotdatapipeline.dataaccess.controller;

import com.iotdatapipeline.dataaccess.service.DeviceService;
import com.iotdatapipeline.dataaccess.service.SensorDataService;
import com.iotdatapipeline.shared.dto.AggregatedData;
import com.iotdatapipeline.shared.dto.DeviceDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Simple test class for IoTDataController without using MockMvc.
 */
@ExtendWith(MockitoExtension.class)
public class IoTDataControllerTest {

    @InjectMocks
    private IoTDataController ioTDataController;  // The controller to be tested

    @Mock
    private DeviceService deviceService;  // Mocking DeviceService

    @Mock
    private SensorDataService sensorDataService;  // Mocking SensorDataService

    // Sample data for testing
    private DeviceDTO device1;
    private DeviceDTO device2;
    private AggregatedData aggregatedData1;
    private AggregatedData aggregatedData2;

    @BeforeEach
    public void setUp() {
        // Initialize mock data before each test
        device1 = new DeviceDTO("1", "Device 1", 1735193041);
        device2 = new DeviceDTO("2", "Device 2", 1735194041);

        aggregatedData1 = new AggregatedData(54.88,26.62,81.36,"°C");
        aggregatedData2 = new AggregatedData(74.24,26.62,81.36,"bpm");
    }

    @Test
    public void testGetDevices() {
        // Arrange: Mock the deviceService to return a list of devices
        List<DeviceDTO> mockDevices = Arrays.asList(device1, device2);
        when(deviceService.getDevices()).thenReturn(mockDevices);

        // Act: Call the controller method
        List<DeviceDTO> devices = ioTDataController.getDevices();

        // Assert: Verify the result
        assertNotNull(devices);
        assertEquals(2, devices.size());
        assertEquals("1", devices.get(0).getId());
        assertEquals("2", devices.get(1).getId());

        // Verify that the deviceService.getDevices() method was called once
        verify(deviceService, times(1)).getDevices();
    }

    @Test
    public void testQueryAggregatedData() {
        // Arrange: Mock the sensorDataService to return a list of aggregated data
        List<AggregatedData> mockAggregatedData = Arrays.asList(aggregatedData1, aggregatedData2);
        when(sensorDataService.queryAggregatedDataForMultipleDevicesAndSensors(
                anyList(), anyList(), anyLong(), anyLong()))
                .thenReturn(mockAggregatedData);

        // Act: Call the controller method with sample input parameters
        List<AggregatedData> aggregatedData = ioTDataController.queryAggregatedData(
                Arrays.asList("1", "2"), Arrays.asList("Temperature", "Humidity"),
                1609459200L, 1609545600L
        );

        // Assert: Verify the result
        assertNotNull(aggregatedData);
        assertEquals(2, aggregatedData.size());
        assertEquals(54.88, aggregatedData.get(0).getAvgValue());
        assertEquals(74.24, aggregatedData.get(1).getAvgValue());

        // Verify that the sensorDataService.queryAggregatedDataForMultipleDevicesAndSensors() method was called once
        verify(sensorDataService, times(1)).queryAggregatedDataForMultipleDevicesAndSensors(
                anyList(), anyList(), anyLong(), anyLong());
    }
}
