package com.fetchrewards.controller;

import com.fetchrewards.dto.GetReceiptPointsResponse;
import com.fetchrewards.dto.ProcessReceiptResponse;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.services.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReceiptControllerTest {

    private ReceiptController receiptController;

    @Mock
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receiptController = new ReceiptController(receiptService);
    }

    @Test
    void testProcessReceipt() {
        Receipt receipt = new Receipt(); // Create a sample receipt
        ProcessReceiptResponse response = new ProcessReceiptResponse("Success");
        when(receiptService.processReceipt(receipt)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<ProcessReceiptResponse> actualResponse = receiptController.processReceipt(receipt);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());

        verify(receiptService, times(1)).processReceipt(receipt);
    }

    @Test
    void testGetReceiptPoints() {
        String receiptId = "123";
        GetReceiptPointsResponse response = new GetReceiptPointsResponse(0); // Provide the required argument
        when(receiptService.getReceiptPoints(receiptId)).thenReturn(ResponseEntity.ok(response));

        ResponseEntity<GetReceiptPointsResponse> actualResponse = receiptController.getReceiptPoints(receiptId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response, actualResponse.getBody());

        verify(receiptService, times(1)).getReceiptPoints(receiptId);
    }
}
