package com.fetchrewards.services;

import com.fetchrewards.dto.ProcessReceiptResponse;
import com.fetchrewards.dto.GetReceiptPointsResponse;
import com.fetchrewards.exceptions.ReceiptNotFoundException;
import com.fetchrewards.exceptions.ReceiptValidationException;
import com.fetchrewards.model.Item;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.repository.ReceiptRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceiptServiceTest {

    private ReceiptService receiptService;

    @Mock
    private PointCalculatorService pointCalculatorService;

    @Mock
    private ReceiptRepository receiptRepository;

    @Mock
    private ReceiptValidationService receiptValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        receiptService = new ReceiptService(pointCalculatorService, receiptRepository, receiptValidationService);
    }

    @Test
    void testProcessReceipt_Success() {
        Receipt receipt = new Receipt(); // Create a sample receipt
        int points = 100;

        // Mocking the void method calls without specifying a return value
        doNothing().when(receiptValidationService).validateReceipt(receipt);
        doNothing().when(receiptRepository).addPointsByReceiptId(anyString(), anyInt());

        when(pointCalculatorService.calculatePoints(receipt)).thenReturn(points);

        ResponseEntity<ProcessReceiptResponse> actualResponse = receiptService.processReceipt(receipt);

        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        assertTrue(actualResponse.getBody() instanceof ProcessReceiptResponse);
        UUID.fromString(actualResponse.getBody().getId());
        verify(receiptValidationService, times(1)).validateReceipt(receipt);
        verify(pointCalculatorService, times(1)).calculatePoints(receipt);
        verify(receiptRepository, times(1)).addPointsByReceiptId(anyString(), anyInt());
    }


    @Test
    void testProcessReceipt_ValidationException() {
        Receipt receipt = new Receipt(); // Create a sample receipt

        // Mocking the void method call to throw an exception
        doThrow(new ReceiptValidationException("Validation failed")).when(receiptValidationService).validateReceipt(receipt);

        assertThrows(ReceiptValidationException.class, () -> {
            receiptService.processReceipt(receipt);
        });

        verify(receiptValidationService, times(1)).validateReceipt(receipt);
    }


    @Test
    void testProcessReceipt_Exception() {
        Receipt receipt = new Receipt(); // Create a sample receipt

        doNothing().when(receiptValidationService).validateReceipt(receipt);
        when(pointCalculatorService.calculatePoints(receipt)).thenThrow(new RuntimeException("Calculation failed"));

        assertThrows(RuntimeException.class, () -> {
            receiptService.processReceipt(receipt);
        });

        verify(receiptValidationService, times(1)).validateReceipt(receipt);
        verify(pointCalculatorService, times(1)).calculatePoints(receipt);
    }



    @Test
    void testGetReceiptPoints_Success() {
        String receiptId = "123";
        int points = 100;
        when(receiptRepository.getPointsByReceiptId(receiptId)).thenReturn(points);
        GetReceiptPointsResponse expectedResponse = new GetReceiptPointsResponse(points);

        ResponseEntity<GetReceiptPointsResponse> actualResponse = receiptService.getReceiptPoints(receiptId);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse, actualResponse.getBody());
        verify(receiptRepository, times(1)).getPointsByReceiptId(receiptId);
    }

    @Test
    void testGetReceiptPoints_InvalidId() {
        String receiptId = null;

        assertThrows(IllegalArgumentException.class, () -> receiptService.getReceiptPoints(receiptId));
        verify(receiptRepository, never()).getPointsByReceiptId(anyString());
    }

    @Test
    void testGetReceiptPoints_NotFoundException() {
        String receiptId = "123";
        when(receiptRepository.getPointsByReceiptId(receiptId)).thenThrow(new IllegalArgumentException("Invalid receipt ID"));

        assertThrows(ReceiptNotFoundException.class, () -> receiptService.getReceiptPoints(receiptId));
        verify(receiptRepository, times(1)).getPointsByReceiptId(receiptId);
    }

    @Test
    void testGetReceiptPoints_Exception() {
        String receiptId = "123";
        when(receiptRepository.getPointsByReceiptId(receiptId)).thenThrow(new RuntimeException("Error fetching points"));

        assertThrows(RuntimeException.class, () -> receiptService.getReceiptPoints(receiptId));
        verify(receiptRepository, times(1)).getPointsByReceiptId(receiptId);
    }

    @Test
    void testProcessReceipt_CalculatePoints_Success() {
        // Create the sample receipt
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate(String.valueOf(LocalDate.parse("2022-01-01")));
        receipt.setPurchaseTime(String.valueOf(LocalTime.parse("13:01")));

        List<Item> items = new ArrayList<>();
        items.add(new Item("Mountain Dew 12PK", "6.49"));
        items.add(new Item("Emils Cheese Pizza", "12.25"));
        items.add(new Item("Knorr Creamy Chicken", "1.26"));
        items.add(new Item("Doritos Nacho Cheese", "3.35"));
        items.add(new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00"));
        receipt.setItems(items);

        receipt.setTotal("35.35");

        // Mock the dependencies and define their behavior
        doNothing().when(receiptValidationService).validateReceipt(receipt);
        doNothing().when(receiptRepository).addPointsByReceiptId(anyString(), anyInt());

        when(pointCalculatorService.calculatePoints(receipt)).thenReturn(28);

        // Perform the actual method invocation
        ResponseEntity<ProcessReceiptResponse> actualResponse = receiptService.processReceipt(receipt);

        // Verify the response and interactions with dependencies
        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        assertTrue(actualResponse.getBody() instanceof ProcessReceiptResponse);

        verify(receiptValidationService, times(1)).validateReceipt(receipt);
        verify(pointCalculatorService, times(1)).calculatePoints(receipt);
        verify(receiptRepository, times(1)).addPointsByReceiptId(anyString(), anyInt());
    }

}
