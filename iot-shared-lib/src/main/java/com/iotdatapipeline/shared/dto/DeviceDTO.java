package com.iotdatapipeline.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceDTO {
    private String id;
    private String deviceType;
}
