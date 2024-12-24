package com.iotdatapipeline.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "devices")  // MongoDB collection for storing devices
public class Device {

    @Id  // Indicates that this field is the primary key for MongoDB
    private String id;
    private String deviceType;
    private long createdAt;
}

