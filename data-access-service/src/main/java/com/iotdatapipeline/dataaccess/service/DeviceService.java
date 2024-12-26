package com.iotdatapipeline.dataaccess.service;

import com.iotdatapipeline.dataaccess.exception.DeviceNotFoundException;
import com.iotdatapipeline.dataaccess.repository.DeviceRepository;
import com.iotdatapipeline.dataaccess.util.DeviceObjectMapperUtil;
import com.iotdatapipeline.shared.dto.DeviceDTO;
import com.iotdatapipeline.shared.model.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Service class for managing IoT devices. Provides methods to add, retrieve, and map devices
 * between the entity and DTO layers. Uses the device repository to interact with the database.
 */
@Service
public class DeviceService {

    private static final Logger logger = Logger.getLogger(DeviceService.class.getName());

    @Autowired
    private DeviceRepository deviceRepository;

    /**
     * Retrieves all devices from the database and maps them to DeviceDTOs.
     *
     * @return A list of DeviceDTOs representing all devices in the database.
     */
    public List<DeviceDTO> getDevices() {
        logger.info("Fetching all devices from the repository.");
        List<Device> devices = deviceRepository.findAll();
        List<DeviceDTO> deviceDTOs = devices.stream()
                .map(DeviceObjectMapperUtil::mapToDeviceDTO)  // Map each device to DeviceDTO
                .collect(Collectors.toList());
        logger.info("Successfully retrieved " + deviceDTOs.size() + " devices.");
        return deviceDTOs;
    }

    /**
     * Retrieves a device by its ID and maps it to a DeviceDTO.
     *
     * @param deviceId The ID of the device to retrieve.
     * @return The DeviceDTO representing the device.
     * @throws DeviceNotFoundException If the device with the given ID is not found.
     */
    public DeviceDTO getDeviceById(String deviceId) {
        logger.info("Fetching device with ID: " + deviceId);
        Optional<Device> device = deviceRepository.findById(deviceId);
        return device.map(DeviceObjectMapperUtil::mapToDeviceDTO)
                .orElseThrow(() -> {
                    logger.warning("Device not found with ID: " + deviceId);
                    return new DeviceNotFoundException(deviceId); // Throw exception if device not found
                });
    }

    /**
     * Adds a new device to the database and returns the corresponding DeviceDTO.
     *
     * @param deviceDTO The DeviceDTO representing the new device to be added.
     * @return The DeviceDTO of the newly added device.
     */
    public DeviceDTO addDevice(DeviceDTO deviceDTO) {
        logger.info("Adding a new device: " + deviceDTO);
        Device device = DeviceObjectMapperUtil.mapToDevice(deviceDTO);
        device.setCreatedAt(System.currentTimeMillis());
        Device savedDevice = deviceRepository.save(device);
        DeviceDTO savedDeviceDTO = DeviceObjectMapperUtil.mapToDeviceDTO(savedDevice);
        logger.info("Successfully added new device with ID: " + savedDevice.getId());
        return savedDeviceDTO;
    }
}
