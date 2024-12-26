package com.iotdatapipeline.dataaccess.repository;

import com.iotdatapipeline.shared.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for interacting with the `SensorData` collection in the MongoDB database.
 * Extends the `MongoRepository` to provide basic CRUD operations for `SensorData` entities.
 *
 * <p>The `SensorDataRepository` includes a custom query method to find sensor data based on device ID, sensor type,
 * and a timestamp range (start and end time).</p>
 *
 * @see MongoRepository
 * @see SensorData
 */
@Repository
public interface SensorDataRepository extends MongoRepository<SensorData, String> {
    // Custom query to find sensor data by deviceId, sensorType, and timestamp range
    @Query("{ 'deviceId' : ?0, 'sensorType' : ?1, 'timestamp' : { $gte: ?2, $lte: ?3 } }")
    List<SensorData> findByDeviceIdAndSensorTypeAndTimestampBetween(
            String deviceId,
            String sensorType,
            long startTime,
            long endTime
    );
}

