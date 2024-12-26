package com.iotdatapipeline.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedData {
    private double avgValue;
    private double minValue;
    private double maxValue;
    private String unit;
}
