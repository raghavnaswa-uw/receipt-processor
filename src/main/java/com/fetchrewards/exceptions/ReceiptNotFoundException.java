package com.fetchrewards.exceptions;

public class ReceiptNotFoundException extends RuntimeException {

    public ReceiptNotFoundException(String message) {
        super(message);
    }
}
