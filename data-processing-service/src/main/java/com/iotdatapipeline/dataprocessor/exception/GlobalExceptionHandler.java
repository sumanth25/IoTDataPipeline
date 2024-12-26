package com.iotdatapipeline.dataprocessor.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle general exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception e) {
        logger.error("An unexpected error occurred: ", e);
        return new ResponseEntity<>("An unexpected error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handle JsonProcessingException
    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<String> handleJsonProcessingException(JsonProcessingException e) {
        logger.error("Error parsing JSON: ", e);
        return new ResponseEntity<>("Failed to parse the incoming message.", HttpStatus.BAD_REQUEST);
    }

    // Handle custom exceptions (for example, for validation or business logic errors)
    @ExceptionHandler(DataProcessingException.class)
    public ResponseEntity<String> handleCustomProcessingException(DataProcessingException e) {
        logger.error("Error during data processing: ", e);
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

