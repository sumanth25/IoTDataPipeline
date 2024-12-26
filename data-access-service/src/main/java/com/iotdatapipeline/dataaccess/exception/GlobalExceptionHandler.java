package com.iotdatapipeline.dataaccess.exception;

import com.iotdatapipeline.shared.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler to manage application exceptions across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles DeviceNotFoundException and returns a response with an appropriate error message.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity with HTTP status code and error message.
     */
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleDeviceNotFoundException(DeviceNotFoundException ex) {
        ErrorResponseDTO ErrorResponseDTO = new ErrorResponseDTO("Device Not Found", ex.getMessage());
        return new ResponseEntity<>(ErrorResponseDTO, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles IllegalArgumentException and returns a response with an appropriate error message.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity with HTTP status code and error message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponseDTO ErrorResponseDTO = new ErrorResponseDTO("Bad Request", ex.getMessage());
        return new ResponseEntity<>(ErrorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles generic exceptions and returns a response with a generic error message.
     *
     * @param ex The exception that was thrown.
     * @return ResponseEntity with HTTP status code and error message.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        ErrorResponseDTO ErrorResponseDTO = new ErrorResponseDTO("Internal Server Error", "An unexpected error occurred.");
        return new ResponseEntity<>(ErrorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

