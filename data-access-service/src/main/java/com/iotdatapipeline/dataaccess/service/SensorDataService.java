package com.iotdatapipeline.dataaccess.service;

import com.iotdatapipeline.dataaccess.repository.SensorDataRepository;
import com.iotdatapipeline.shared.dto.AggregatedData;
import com.iotdatapipeline.shared.model.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class SensorDataService {

    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public List<AggregatedData> queryAggregatedDataForMultipleDevicesAndSensors(
            List<String> deviceIds,
            List<String> sensorTypes,
            long startTime,
            long endTime) {

        // Validate that both lists have the same size
        if (deviceIds.size() != sensorTypes.size()) {
            throw new IllegalArgumentException("The number of deviceIds and sensorTypes must be equal");
        }

        // Query the aggregated data for each combination of deviceId and sensorType (matching by index)
        List<AggregatedData> aggregatedDataList = IntStream.range(0, deviceIds.size())
                .mapToObj(i -> {
                    String deviceId = deviceIds.get(i);
                    String sensorType = sensorTypes.get(i);

                    // Log each query before calling the service
                    System.out.println("Fetching data for deviceId: " + deviceId
                            + ", sensorType: " + sensorType
                            + ", startTime: " + startTime
                            + ", endTime: " + endTime);

                    AggregatedData aggregatedData = getAggregatedData(deviceId, sensorType, startTime, endTime);

                    if (aggregatedData == null) {
                        System.out.println("No aggregated data found for deviceId: " + deviceId
                                + ", sensorType: " + sensorType);
                    }

                    return aggregatedData;
                })
                .filter(aggregatedData -> aggregatedData != null) // Filter out null results
                .collect(Collectors.toList());

        return aggregatedDataList;
    }

    protected AggregatedData getAggregatedData(String deviceId, String sensorType, long startTime, long endTime) {
        // Use the repository to find data within the specified range
        List<SensorData> data = sensorDataRepository.findByDeviceIdAndSensorTypeAndTimestampBetween(
                deviceId, sensorType, startTime, endTime
        );

        // Aggregation logic (avg, min, max)
        AggregatedData aggregatedData = new AggregatedData();

        // Ensure there is data to aggregate
        if (!data.isEmpty()) {
            List<Double> values = data.stream().map(SensorData::getValue).collect(Collectors.toList());

            OptionalDouble avg = values.stream().mapToDouble(Double::doubleValue).average();
            aggregatedData.setAvgValue(avg.orElse(0.0));

            OptionalDouble min = values.stream().mapToDouble(Double::doubleValue).min();
            aggregatedData.setMinValue(min.orElse(0.0));

            OptionalDouble max = values.stream().mapToDouble(Double::doubleValue).max();
            aggregatedData.setMaxValue(max.orElse(0.0));

            aggregatedData.setUnit(data.get(0).getUnit()); // Assuming unit is the same across all data
        }

        return aggregatedData;
    }
}
