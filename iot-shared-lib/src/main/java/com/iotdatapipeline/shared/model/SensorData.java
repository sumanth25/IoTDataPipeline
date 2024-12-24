package com.iotdatapipeline.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "sensor_data") // MongoDB collection name
public class SensorData {

    @Id
    private String id;  // MongoDB _id field
    private String deviceId;
    private String sensorType;
    private double value;
    private String unit;
    private long timestamp;
}

