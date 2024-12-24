package com.iotdatapipeline.shared.dto;

import lombok.Data;

@Data
public class AggregatedData {
    private double avgValue;
    private double minValue;
    private double maxValue;
    private String unit;
}
