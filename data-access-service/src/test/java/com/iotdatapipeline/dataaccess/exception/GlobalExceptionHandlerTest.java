package com.iotdatapipeline.dataaccess.exception;

import com.iotdatapipeline.shared.dto.ErrorResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private DeviceNotFoundException deviceNotFoundException;

    @Mock
    private IllegalArgumentException illegalArgumentException;

    @BeforeEach
    public void setUp() {
        // Setup can be customized if needed
    }

    /**
     * Test for handling DeviceNotFoundException
     */
    @Test
    public void testHandleDeviceNotFoundException() {
        String deviceId = "12345";
        when(deviceNotFoundException.getMessage()).thenReturn("Device not found with ID: " + deviceId);

        ResponseEntity<ErrorResponseDTO> responseEntity = globalExceptionHandler.handleDeviceNotFoundException(deviceNotFoundException);
        ErrorResponseDTO errorResponse = responseEntity.getBody();

        assertNotNull(errorResponse);
        assertEquals("Device Not Found", errorResponse.getErrorType());
        assertEquals("Device not found with ID: " + deviceId, errorResponse.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    /**
     * Test for handling IllegalArgumentException
     */
    @Test
    public void testHandleIllegalArgumentException() {
        String errorMessage = "Invalid argument!";
        when(illegalArgumentException.getMessage()).thenReturn(errorMessage);

        ResponseEntity<ErrorResponseDTO> responseEntity = globalExceptionHandler.handleIllegalArgumentException(illegalArgumentException);
        ErrorResponseDTO errorResponse = responseEntity.getBody();

        assertNotNull(errorResponse);
        assertEquals("Bad Request", errorResponse.getErrorType());
        assertEquals(errorMessage, errorResponse.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    /**
     * Test for handling generic exceptions
     */
    @Test
    public void testHandleGenericException() {
        Exception exception = new Exception("Unexpected error!");

        ResponseEntity<ErrorResponseDTO> responseEntity = globalExceptionHandler.handleGenericException(exception);
        ErrorResponseDTO errorResponse = responseEntity.getBody();

        assertNotNull(errorResponse);
        assertEquals("Internal Server Error", errorResponse.getErrorType());
        assertEquals("An unexpected error occurred.", errorResponse.getMessage());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}

