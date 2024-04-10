package com.darwgom.candidatecontrolapi.domain.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    public void handleEntityNotFoundException_ReturnsErrorResponseWithBadRequestStatus() {
        EntityNotFoundException exception = new EntityNotFoundException("Entity not found");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Entity not found", responseEntity.getBody().message());
    }

    @Test
    public void handleIllegalParamException_ReturnsErrorResponseWithBadRequestStatus() {
        IllegalParamException exception = new IllegalParamException("Illegal parameter");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleIllegalParamException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Illegal parameter", responseEntity.getBody().message());
    }

    @Test
    public void handleValueAlreadyExistsException_ReturnsErrorResponseWithBadRequestStatus() {
        ValueAlreadyExistsException exception = new ValueAlreadyExistsException("Value already exists");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleValueAlreadyExistsException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Value already exists", responseEntity.getBody().message());
    }

    @Test
    public void handleValueNotFoundException_ReturnsErrorResponseWithBadRequestStatus() {
        ValueNotFoundException exception = new ValueNotFoundException("Value not found");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleValueNotFoundException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Value not found", responseEntity.getBody().message());
    }
}

