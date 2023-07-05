package com.fetchrewards.services;

import com.fetchrewards.dto.ProcessReceiptResponse;
import com.fetchrewards.dto.GetReceiptPointsResponse;
import com.fetchrewards.exceptions.ReceiptNotFoundException;
import com.fetchrewards.exceptions.ReceiptValidationException;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReceiptService {
    private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

    private final PointCalculatorService pointCalculatorService;
    private final ReceiptRepository receiptRepository;
    private final ReceiptValidationService receiptValidationService;

    @Autowired
    public ReceiptService(PointCalculatorService pointCalculatorService, ReceiptRepository receiptRepository, ReceiptValidationService receiptValidationService) {
        this.pointCalculatorService = pointCalculatorService;
        this.receiptRepository = receiptRepository;
        this.receiptValidationService = receiptValidationService;
    }

    /**
     * Processes a receipt by validating it, generating a new ID, calculating the points, and storing the points in the repository.
     *
     * @param receipt The receipt to be processed.
     * @return The response entity containing the ID of the processed receipt.
     * @throws ReceiptValidationException if the receipt validation fails.
     * @throws Exception                 if an error occurs during the processing of the receipt.
     */
    public ResponseEntity<ProcessReceiptResponse> processReceipt(Receipt receipt) {
        try {
            receiptValidationService.validateReceipt(receipt);
            logger.info("Receipt Validated");
            String id = generateReceiptId();
            logger.info("New id generated for receipt");
            int points = pointCalculatorService.calculatePoints(receipt);
            receiptRepository.addPointsByReceiptId(id, points);
            ProcessReceiptResponse response = new ProcessReceiptResponse(id);
            logger.info("Processed receipt with ID: {}", id);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (ReceiptValidationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error processing receipt", e);
            throw e;
        }
    }

    /**
     * Retrieves the points awarded for a receipt with the specified ID.
     *
     * @param id The ID of the receipt.
     * @return The response entity containing the number of points awarded.
     * @throws IllegalArgumentException if the ID is null or empty.
     * @throws ReceiptNotFoundException  if the receipt is not found in the repository.
     * @throws Exception                 if an error occurs while getting the receipt points.
     */
    public ResponseEntity<GetReceiptPointsResponse> getReceiptPoints(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }

        try {
            int points = receiptRepository.getPointsByReceiptId(id);
            logger.info("Points fetched for ID: {}", id);
            GetReceiptPointsResponse response = new GetReceiptPointsResponse(points);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException iaException) {
            logger.error("Receipt not found for ID: {}", id);
            throw new ReceiptNotFoundException("Receipt not found for ID: " + id);
        } catch (Exception e) {
            logger.error("Error getting receipt points", e);
            throw e;
        }
    }

    private String generateReceiptId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
