package com.iotdatapipeline.dataaccess.repository;

import com.iotdatapipeline.shared.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for interacting with the `Device` collection in the MongoDB database.
 * Extends the `MongoRepository` to provide basic CRUD operations for `Device` entities.
 *
 * <p>The `DeviceRepository` allows searching for devices by their unique identifier (ID).
 * It also inherits the default repository methods such as saving, updating, and deleting `Device` entities.</p>
 *
 * @see MongoRepository
 * @see Device
 */
@Repository
public interface DeviceRepository extends MongoRepository<Device, String> {
    Optional<Device> findById(String id);
}

