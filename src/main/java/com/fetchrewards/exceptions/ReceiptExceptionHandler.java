package com.fetchrewards.exceptions;

import com.fetchrewards.dto.ProcessReceiptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.fetchrewards.dto.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@RestControllerAdvice
public class ReceiptExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptExceptionHandler.class);

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReceiptNotFoundException(ReceiptNotFoundException ex) {
        logger.error("Receipt not found: {}", ex.getMessage());
        ErrorResponse errorResponse  = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ReceiptValidationException.class)
    public ResponseEntity<ErrorResponse> handleReceiptValidationException(ReceiptValidationException ex) {
        logger.error("Invalid receipt {}", ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
