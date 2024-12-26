package com.iotdatapipeline.dataprocessor.repository;

import com.iotdatapipeline.shared.model.SensorData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface extends {@link MongoRepository}, which provides a variety of CRUD operations
 * for managing {@link SensorData} entities. By extending {@link MongoRepository}, it allows
 * for automatic implementation of methods such as save, find, delete, etc. without needing
 * to write custom implementations.
 */
@Repository
public interface ProcessingDataRepository extends MongoRepository<SensorData, String> {

}
