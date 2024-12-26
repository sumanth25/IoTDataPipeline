package com.iotdatapipeline.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A custom error response DTO to be returned for errors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponseDTO {
    private String errorType;
    private String message;
}

