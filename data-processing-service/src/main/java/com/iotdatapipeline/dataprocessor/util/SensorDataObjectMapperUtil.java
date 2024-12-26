package com.iotdatapipeline.dataprocessor.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import com.iotdatapipeline.shared.model.SensorData;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * Utility class for mapping between SensorData and SensorDataDTO objects.
 */
@Component
public class SensorDataObjectMapperUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();
    // Map SensorData entity to SensorDataDTO
    public SensorDataDTO mapToSensorDataDTO(SensorData sensorData) {
        return objectMapper.convertValue(sensorData, SensorDataDTO.class);
    }

    // Map SensorDataDTO back to SensorData entity
    public SensorData mapToSensorData(SensorDataDTO sensorDataDTO) {
        return objectMapper.convertValue(sensorDataDTO, SensorData.class);
    }

    // Map object to JSON string (optional)
    public String objectToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    // Map JSON string to object (optional)
    public <T> T jsonToObject(String json, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(json, valueType);
    }
}


