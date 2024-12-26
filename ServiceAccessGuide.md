# IoT Data Pipeline: Setup and Service Access Guide

This guide explains how to set up the entire **IoTDataPipeline** with Docker, Kafka, MongoDB, and multiple Spring Boot services. Follow these steps to get your environment up and running.

## Project Modules

The **IoTDataPipeline** consists of the following modules:

1. **data-access-service**: A Spring Boot service running on port `8082`. It provides an aggregation endpoint for accessing the data.
2. **data-processing-service**: A Spring Boot service running on port `8081`. It processes IoT device data consumed from Kafka and stores it in MongoDB.
3. **iot-device-simulator**: A Spring Boot service running on port `8081`. It simulates IoT devices and sends data to Kafka every second.

## Prerequisites

1. **Docker** installed on your local machine.
2. **Java** installed (for Spring Boot applications).
3. **Docker Compose** (optional, but recommended for managing multiple services).

## Steps to Run the Services

## Project Modules

The **IoTDataPipeline** consists of the following modules:

1. **data-access-service**: A Spring Boot service running on port `8082`. It provides an aggregation endpoint for accessing the data.
2. **data-processing-service**: A Spring Boot service running on port `8081`. It processes IoT device data consumed from Kafka and stores it in MongoDB.
3. **iot-device-simulator**: A Spring Boot service running on port `8081`. It simulates IoT devices and sends data to Kafka every second.

## Prerequisites

1. **Docker** installed on your local machine.
2. **Java** installed (for Spring Boot applications).
3. **Docker Compose** (optional, but recommended for managing multiple services).

## Steps to Run the Services

### 1. Run Docker Locally

Ensure that Docker is installed and running on your machine. You can verify this by running the following command:

docker --version

### 2. Pull and Run MongoDB Docker Image

First, pull the latest MongoDB image with the default port `27017`:

docker pull mongo:latest

### 3.Start the iot-device-simulator Service

cd iot-device-simulator
./mvnw spring-boot:run

### 4.Start the data-processing-service Service

cd data-processing-service
./mvnw spring-boot:run

### 5.Start the data-access-service Service

cd data-access-service
./mvnw spring-boot:run

### 6.Access Aggregated Data

Once all services are running, you can access the aggregated data through the data-access-service. The service exposes an endpoint that aggregates data from multiple devices and sensor types using the IOT_DataAccess_Insomnia_2024-12-26.json collection having REST APIs

Ex: http://localhost:8082/api/v1/aggregated?deviceIds=device_thermostat,device_heart_rate,device_fuel&sensorTypes=temperature,heart_rate,fuel_level&startTime=1735208155491&endTime=1735208162501

# Summary of Services and Ports

The IoT Data Pipeline consists of multiple services and components. Below is a summary of each service, its respective port, and its functionality.

## Services and Ports

- **MongoDB**:  
  Port: `27017`  
  Database: `sensor_data_db`  
  Purpose: Stores IoT device data. MongoDB is used to persist the data processed by the data-processing-service.

- **Kafka**:  
  Port: `9092`  
  Purpose: Kafka is used as the message broker for streaming data from the **iot-device-simulator** to the **data-processing-service**. It facilitates the real-time data flow.

- **iot-device-simulator**:  
  Port: `8080`  
  Purpose: Simulates IoT devices and sends data to Kafka every second. This service generates mock sensor data to mimic real-world IoT device inputs.

- **data-processing-service**:  
  Port: `8081`  
  Purpose: Consumes IoT device data from Kafka, processes it, and stores it into MongoDB. This service handles the data transformation and storage process.

- **data-access-service**:  
  Port: `8082`  
  Purpose: Exposes an aggregation endpoint for accessing processed IoT device data. The data is aggregated and made available through this service.

## Notes:

- Ensure that **MongoDB** and **Kafka** are running before starting the Spring Boot services.
- The **IoT device simulator** will send data every second, and the **data-processing-service** will process and store the data accordingly.
- The **data-access-service** will allow you to access the aggregated data through the `/aggregated` endpoint.

With this setup, you can now simulate, process, and access aggregated IoT device data seamlessly in your local environment.