package com.iotdatapipeline.dataprocessor.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iotdatapipeline.dataprocessor.exception.DataProcessingException;
import com.iotdatapipeline.dataprocessor.service.ProcessingDataService;
import com.iotdatapipeline.dataprocessor.util.SensorDataObjectMapperUtil;
import com.iotdatapipeline.shared.dto.SensorDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Consumer class that listens to Kafka messages from the "sensor-topic",
 * processes the sensor data, and stores it in a MongoDB database.
 * <p>
 * This class is responsible for parsing incoming Kafka messages,
 * processing the sensor data, and saving it using the ProcessingDataService.
 * It also includes error handling for issues such as JSON parsing or data storage errors.
 * </p>
 */
@EnableKafka
@Service
public class SensorDataConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SensorDataConsumer.class);

    private final ProcessingDataService processingDataService;
    private final SensorDataObjectMapperUtil sensorDataObjectMapperUtil;

    /**
     * Constructor that injects the {@link ProcessingDataService} dependency.
     *
     * @param processingDataService The service used to save processed sensor data.
     */
    @Autowired
    public SensorDataConsumer(ProcessingDataService processingDataService,SensorDataObjectMapperUtil sensorDataObjectMapperUtil) {
        this.processingDataService = processingDataService;
        this.sensorDataObjectMapperUtil = sensorDataObjectMapperUtil;
    }

    /**
     * Consumes messages from the Kafka topic "sensor-topic". This method is triggered
     * when a new message is received, parses the message into a SensorDataDTO object,
     * processes it, and stores the data.
     *
     * @param recordValue The raw message received from the Kafka topic, in JSON format.
     * @throws DataProcessingException If there is an error processing the message.
     */
    @KafkaListener(topics = "sensor-topic", groupId = "sensor-consumer-group")
    public void consume(String recordValue) {
        try {
            logger.info("Received message: {}", recordValue);

            // Parse the incoming message (JSON) to SensorDataDTO
            SensorDataDTO sensorData = parseSensorData(recordValue);

            // Process and store the data
            processAndStoreSensorData(sensorData);

        } catch (JsonProcessingException e) {
            logger.error("Failed to parse incoming message: {}", recordValue, e);
            throw new DataProcessingException("Error parsing JSON message.", e); // Throw custom exception for global handler
        } catch (Exception e) {
            logger.error("Error processing the Kafka message: ", e);
            throw new DataProcessingException("Error processing message.", e); // Handle generic exception here
        }
    }

    /**
     * Processes the parsed sensor data and saves it to the database using the
     * {@link ProcessingDataService}.
     *
     * @param sensorData The SensorDataDTO object that contains the parsed sensor data.
     * @throws DataProcessingException If there is an error saving the sensor data.
     */
    private void processAndStoreSensorData(SensorDataDTO sensorData) {
        try {
            logger.info("Processing and storing sensor data: {}", sensorData);
            processingDataService.saveSensorData(sensorData);
            logger.info("Sensor data saved successfully: {}", sensorData);
        } catch (Exception e) {
            logger.error("Error saving sensor data: {}", sensorData, e);
            throw new DataProcessingException("Error saving sensor data", e); // Throw exception to be caught globally
        }
    }

    /**
     * Parses the incoming Kafka message (in JSON format) into a {@link SensorDataDTO} object.
     *
     * @param recordValue The raw Kafka message in JSON format.
     * @return A {@link SensorDataDTO} object representing the parsed data.
     * @throws JsonProcessingException If there is an error during JSON parsing.
     */
    protected SensorDataDTO parseSensorData(String recordValue) throws JsonProcessingException {
        logger.debug("Parsing record value: {}", recordValue);
        return sensorDataObjectMapperUtil.jsonToObject(recordValue, SensorDataDTO.class);
    }
}
