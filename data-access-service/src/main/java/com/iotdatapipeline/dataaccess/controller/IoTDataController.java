package com.iotdatapipeline.dataaccess.controller;

import com.iotdatapipeline.dataaccess.service.DeviceService;
import com.iotdatapipeline.dataaccess.service.SensorDataService;
import com.iotdatapipeline.shared.dto.AggregatedData;
import com.iotdatapipeline.shared.dto.DeviceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

/**
 * Controller class for managing IoT device data ingestion, aggregation, and querying.
 * Provides endpoints to query aggregated sensor data, manage devices, and add new devices.
 */
@RestController
@RequestMapping("/api/v1")
@Tag(name = "IoT Data Pipeline API", description = "API for managing IoT device data ingestion, aggregation, and querying.")
public class IoTDataController {

    private static final Logger logger = Logger.getLogger(IoTDataController.class.getName());

    @Autowired
    private SensorDataService sensorDataService;

    @Autowired
    private DeviceService deviceService;

    /**
     * Endpoint to query aggregated sensor data for multiple devices and sensor types.
     *
     * @param deviceIds The IDs of the IoT devices to filter by (comma-separated).
     * @param sensorTypes The types of sensor data to filter by (comma-separated).
     * @param startTime The start of the timeframe for aggregation (in Unix timestamp in seconds (UTC)).
     * @param endTime The end of the timeframe for aggregation (in Unix timestamp in seconds (UTC)).
     * @return A list of aggregated sensor data for the given parameters.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved aggregated data.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AggregatedData.class))),
            @ApiResponse(responseCode = "400", description = "Bad request (invalid parameters)."),
            @ApiResponse(responseCode = "404", description = "Aggregated data not found for the given timeframe."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/aggregated")
    public List<AggregatedData> queryAggregatedData(
            @Parameter(description = "The IDs of the IoT devices to filter by (comma-separated).")
            @RequestParam @NotNull List<String> deviceIds,

            @Parameter(description = "The types of sensor data to filter by (comma-separated).")
            @RequestParam @NotNull List<String> sensorTypes,

            @Parameter(description = "The start of the timeframe for aggregation (in Unix timestamp in seconds (UTC)).")
            @RequestParam @NotNull @Positive long startTime,

            @Parameter(description = "The end of the timeframe for aggregation (in Unix timestamp in seconds (UTC)).")
            @RequestParam @NotNull @Positive long endTime
    ) {
        logger.info("Received request to query aggregated data for deviceIds: " + deviceIds + " and sensorTypes: " + sensorTypes);

        if (startTime > endTime) {
            logger.warning("Invalid timeframe: startTime cannot be greater than endTime.");
            throw new IllegalArgumentException("startTime must be less than or equal to endTime");
        }

        // Query the aggregated data for each combination of deviceId and sensorType
        List<AggregatedData> aggregatedDataList = sensorDataService.queryAggregatedDataForMultipleDevicesAndSensors(deviceIds, sensorTypes, startTime, endTime);

        if (aggregatedDataList.isEmpty()) {
            logger.info("No aggregated data found for the given parameters: deviceIds: " + deviceIds + ", sensorTypes: " + sensorTypes + ", startTime: " + startTime + ", endTime: " + endTime);
        } else {
            logger.info("Successfully retrieved aggregated data for deviceIds: " + deviceIds + " and sensorTypes: " + sensorTypes);
        }

        return aggregatedDataList;
    }

    /**
     * Endpoint to retrieve a list of all IoT devices registered in the system.
     *
     * @return A list of device DTOs representing the IoT devices in the system.
     */
    @GetMapping("/devices")
    @Operation(
            summary = "Retrieve a list of IoT devices",
            description = "Retrieves a list of all IoT devices registered in the system.",
            tags = {"Devices"}
    )
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of devices.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class)))
    public List<DeviceDTO> getDevices() {
        logger.info("Received request to retrieve the list of IoT devices.");
        List<DeviceDTO> devices = deviceService.getDevices();
        logger.info("Successfully retrieved " + devices.size() + " devices.");
        return devices;
    }

    /**
     * Endpoint to add a new IoT device to the system.
     *
     * @param newDevice The device DTO containing information of the new device to be added.
     * @return The added device DTO.
     */
    @PostMapping("/devices")
    @Operation(
            summary = "Add a new IoT device",
            description = "Adds a new IoT device to the system.",
            tags = {"Devices"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully added a new device.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request (invalid data)."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public DeviceDTO addDevice(@RequestBody @Validated DeviceDTO newDevice) {
        logger.info("Received request to add a new device: " + newDevice);
        DeviceDTO addedDevice = deviceService.addDevice(newDevice);
        logger.info("Successfully added new device: " + addedDevice);
        return addedDevice;
    }

    /**
     * Endpoint to retrieve detailed information of an IoT device by its unique device ID.
     *
     * @param deviceId The ID of the IoT device whose details are to be retrieved.
     * @return The device DTO containing the details of the specified device.
     * @throws DeviceNotFoundException If the device with the given device ID is not found.
     */
    @GetMapping("/devices/{deviceId}")
    @Operation(
            summary = "Retrieve details of a specific IoT device",
            description = "Retrieves detailed information of an IoT device by its unique device ID.",
            tags = {"Devices"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved device details.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceDTO.class))),
            @ApiResponse(responseCode = "404", description = "Device not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public DeviceDTO getDeviceById(@PathVariable String deviceId) {
        logger.info("Received request to retrieve device details for deviceId: " + deviceId);
        DeviceDTO device = deviceService.getDeviceById(deviceId);
        logger.info("Successfully retrieved device details for deviceId: " + deviceId);
        return device;
    }
}
