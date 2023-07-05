package com.fetchrewards.exceptions;

public class ReceiptValidationException extends RuntimeException {
    public ReceiptValidationException(String message) {
        super(message);
    }
}
