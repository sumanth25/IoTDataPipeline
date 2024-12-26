package com.iotdatapipeline.dataprocessor.service;

import com.iotdatapipeline.dataprocessor.exception.DataProcessingException;
import com.iotdatapipeline.dataprocessor.repository.ProcessingDataRepository;
import com.iotdatapipeline.dataprocessor.util.SensorDataObjectMapperUtil;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import com.iotdatapipeline.shared.model.SensorData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Ensure MockitoExtension is loaded
public class ProcessingDataServiceTest {

    @Mock
    private ProcessingDataRepository processingDataRepository;

    @Mock
    private SensorDataObjectMapperUtil sensorDataObjectMapperUtil;  // Mock this object

    @InjectMocks
    private ProcessingDataService processingDataService;  // Service under test

    private SensorDataDTO sensorDataDTO;

    @BeforeEach
    public void setUp() {
        // Prepare a sample SensorDataDTO
        sensorDataDTO = new SensorDataDTO();
        sensorDataDTO.setDeviceId("12345");
        sensorDataDTO.setSensorType("temperature");
        sensorDataDTO.setValue(23.5);
        sensorDataDTO.setUnit("bpm");
        sensorDataDTO.setTimestamp(1735188696);
    }

    @Test
    public void testSaveSensorData_Success() {
        // Arrange
        SensorData sensorData = new SensorData();
        sensorData.setDeviceId("12345");
        sensorData.setSensorType("temperature");
        sensorData.setValue(23.5);
        sensorData.setUnit("bpm");
        sensorData.setTimestamp(1735188696);

        // Mock the mapToSensorData method properly (ensure it's called on the mock)
        when(sensorDataObjectMapperUtil.mapToSensorData(sensorDataDTO)).thenReturn(sensorData);

        // Act
        processingDataService.saveSensorData(sensorDataDTO);

        // Assert
        verify(processingDataRepository, times(1)).save(sensorData);
    }

    @Test
    public void testSaveSensorData_ExceptionThrown() {
        // Arrange
        SensorData sensorData = new SensorData();
        sensorData.setDeviceId("12345");

        // Mock the mapToSensorData method to throw an exception
        when(sensorDataObjectMapperUtil.mapToSensorData(sensorDataDTO))
                .thenThrow(new RuntimeException("Error in mapping"));

        // Act & Assert
        DataProcessingException exception = assertThrows(DataProcessingException.class,
                () -> processingDataService.saveSensorData(sensorDataDTO));
        assertEquals("Error processing and saving sensor data.", exception.getMessage());
    }

    @Test
    public void testSaveSensorData_SavingFailure() {
        // Arrange
        SensorData sensorData = new SensorData();
        sensorData.setDeviceId("12345");

        // Mock the mapToSensorData method
        when(sensorDataObjectMapperUtil.mapToSensorData(sensorDataDTO)).thenReturn(sensorData);

        // Simulate repository save failure
        doThrow(new RuntimeException("Repository save failed")).when(processingDataRepository).save(sensorData);

        // Act & Assert
        DataProcessingException exception = assertThrows(DataProcessingException.class,
                () -> processingDataService.saveSensorData(sensorDataDTO));
        assertEquals("Error processing and saving sensor data.", exception.getMessage());
    }
}
