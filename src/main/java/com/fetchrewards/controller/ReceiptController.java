package com.fetchrewards.controller;

import com.fetchrewards.dto.GetReceiptPointsResponse;
import com.fetchrewards.dto.ProcessReceiptResponse;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.services.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptController.class);

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Process a receipt and return the response.
     *
     * @param receipt The receipt to be processed.
     * @return ResponseEntity containing the process receipt response.
     */
    @PostMapping("/process")
    public ResponseEntity<ProcessReceiptResponse> processReceipt(@RequestBody Receipt receipt) {
        logger.info("Processing receipt: {}", receipt);
        ResponseEntity<ProcessReceiptResponse> response = receiptService.processReceipt(receipt);
        logger.info("Receipt processed successfully");
        return response;
    }

    /**
     * Get the points awarded for a receipt with the specified ID.
     *
     * @param id The ID of the receipt.
     * @return ResponseEntity containing the receipt points response.
     */
    @GetMapping("/{id}/points")
    public ResponseEntity<GetReceiptPointsResponse> getReceiptPoints(@PathVariable String id) {
        logger.info("Fetching points for receipt with ID: {}", id);
        ResponseEntity<GetReceiptPointsResponse> response = receiptService.getReceiptPoints(id);
        logger.info("Points fetched successfully");
        return response;
    }
}
