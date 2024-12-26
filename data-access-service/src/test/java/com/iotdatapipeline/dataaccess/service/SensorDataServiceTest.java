package com.iotdatapipeline.dataaccess.service;

import com.iotdatapipeline.dataaccess.repository.SensorDataRepository;
import com.iotdatapipeline.shared.dto.AggregatedData;
import com.iotdatapipeline.shared.model.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SensorDataServiceTest {

    @Mock
    private SensorDataRepository sensorDataRepository;

    @InjectMocks
    private SensorDataService sensorDataService;

    private List<String> deviceIds;
    private List<String> sensorTypes;
    private long startTime;
    private long endTime;

    @BeforeEach
    void setUp() {
        deviceIds = Arrays.asList("device1", "device2");
        sensorTypes = Arrays.asList("temperature", "humidity");
        startTime = 1000L;
        endTime = 2000L;
    }

    @Test
    void testQueryAggregatedDataForMultipleDevicesAndSensors_Success() {
        // Mock the data returned by the repository
        SensorData sensorData1 = new SensorData("1","device1", "temperature", 25.5, "Celsius",1735193041);
        SensorData sensorData2 = new SensorData("2","device2", "humidity", 60.0, "Percentage",1735193041);
        List<SensorData> sensorDataList1 = Arrays.asList(sensorData1);
        List<SensorData> sensorDataList2 = Arrays.asList(sensorData2);

        // Mock repository response
        when(sensorDataRepository.findByDeviceIdAndSensorTypeAndTimestampBetween(
                "device1", "temperature", startTime, endTime
        )).thenReturn(sensorDataList1);

        when(sensorDataRepository.findByDeviceIdAndSensorTypeAndTimestampBetween(
                "device2", "humidity", startTime, endTime
        )).thenReturn(sensorDataList2);

        // Call the service method
        List<AggregatedData> result = sensorDataService.queryAggregatedDataForMultipleDevicesAndSensors(
                deviceIds, sensorTypes, startTime, endTime
        );

        // Verify the result
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(25.5, result.get(0).getAvgValue());
        assertEquals(60.0, result.get(1).getAvgValue());
    }

    @Test
    void testQueryAggregatedDataForMultipleDevicesAndSensors_ArgumentMismatch() {
        // Different sizes for deviceIds and sensorTypes
        List<String> wrongSensorTypes = Arrays.asList("temperature");

        // Verify exception is thrown when the sizes don't match
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sensorDataService.queryAggregatedDataForMultipleDevicesAndSensors(
                    deviceIds, wrongSensorTypes, startTime, endTime
            );
        });

        assertEquals("The number of deviceIds and sensorTypes must be equal", exception.getMessage());
    }

    @Test
    void testGetAggregatedData_NoDataToAggregate() {
        // Mock repository response for no data
        when(sensorDataRepository.findByDeviceIdAndSensorTypeAndTimestampBetween(
                "device1", "temperature", startTime, endTime
        )).thenReturn(Collections.emptyList());

        // Call the aggregation method
        AggregatedData aggregatedData = sensorDataService.getAggregatedData("device1", "temperature", startTime, endTime);

        // Verify that default values (0.0) are returned for aggregation
        assertNotNull(aggregatedData);
        assertEquals(0.0, aggregatedData.getAvgValue());
        assertEquals(0.0, aggregatedData.getMinValue());
        assertEquals(0.0, aggregatedData.getMaxValue());
    }
}
