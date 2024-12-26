package com.iotdatapipeline.dataprocessor.service;

import com.iotdatapipeline.dataprocessor.exception.DataProcessingException;
import com.iotdatapipeline.dataprocessor.repository.ProcessingDataRepository;
import com.iotdatapipeline.dataprocessor.util.SensorDataObjectMapperUtil;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import com.iotdatapipeline.shared.model.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessingDataService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessingDataService.class);

    private final ProcessingDataRepository processingDataRepository;
    private final SensorDataObjectMapperUtil sensorDataObjectMapperUtil;  // Inject this as a non-static instance

    @Autowired
    public ProcessingDataService(ProcessingDataRepository processingDataRepository,
                                 SensorDataObjectMapperUtil sensorDataObjectMapperUtil) {
        this.processingDataRepository = processingDataRepository;
        this.sensorDataObjectMapperUtil = sensorDataObjectMapperUtil;
    }

    /**
     * Saves sensor data by converting the SensorDataDTO to SensorData
     * and persisting it to the MongoDB repository.
     *
     * @param sensorDataDTO The DTO object containing sensor data.
     */
    public void saveSensorData(SensorDataDTO sensorDataDTO) {
        logger.info("Received request to save sensor data.");

        try {
            // Log the received DTO before mapping
            logger.debug("Mapping SensorDataDTO to SensorData. DTO: {}", sensorDataDTO);

            // Map the DTO to a SensorData entity
            SensorData sensorData = sensorDataObjectMapperUtil.mapToSensorData(sensorDataDTO);  // Call instance method

            // Log the mapping result
            logger.debug("Mapped SensorDataDTO to SensorData. Mapped object: {}", sensorData);

            // Save the SensorData to the repository
            logger.info("Saving SensorData to repository: {}", sensorData);
            processingDataRepository.save(sensorData);
            logger.info("SensorData saved successfully. ID: {}", sensorData.getId());
        } catch (Exception e) {
            // Log the error in case of failure and throw a custom exception
            logger.error("Failed to save SensorData. Error: {}", e.getMessage(), e);
            throw new DataProcessingException("Error processing and saving sensor data.", e);
        }
    }
}

