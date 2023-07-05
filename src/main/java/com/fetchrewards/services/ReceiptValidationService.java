package com.fetchrewards.services;

import com.fetchrewards.exceptions.ReceiptValidationException;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.model.Item;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class ReceiptValidationService {
    /**
     * Validates a receipt by checking if its properties are not null or empty.
     *
     * @param receipt The receipt to validate.
     * @throws ReceiptValidationException if the receipt is null or any of its properties are empty.
     */
    public void validateReceipt(Receipt receipt) {
        validateNotNull(receipt);
        validateRetailer(receipt.getRetailer());
        validatePurchaseDate(receipt.getPurchaseDate());
        validatePurchaseTime(receipt.getPurchaseTime());
        validateTotal(receipt.getTotal());
        validateItems(receipt.getItems());
    }

    private void validateNotNull(Receipt receipt) {
        if (ObjectUtils.isEmpty(receipt)) {
            throw new ReceiptValidationException("Receipt cannot be null.");
        }
    }

    private void validateRetailer(String retailer) {
        if (ObjectUtils.isEmpty(retailer)) {
            throw new ReceiptValidationException("Retailer must not be empty.");
        }
    }

    private void validatePurchaseDate(String purchaseDate) {
        if (ObjectUtils.isEmpty(purchaseDate)) {
            throw new ReceiptValidationException("Purchase date must not be empty.");
        }
    }

    private void validatePurchaseTime(String purchaseTime) {
        if (ObjectUtils.isEmpty(purchaseTime)) {
            throw new ReceiptValidationException("Purchase time must not be empty.");
        }
    }

    private void validateTotal(String total) {
        if (ObjectUtils.isEmpty(total)) {
            throw new ReceiptValidationException("Total must not be empty.");
        }
    }

    private void validateItems(List<Item> items) {
        if (ObjectUtils.isEmpty(items)) {
            throw new ReceiptValidationException("Items list must not be empty.");
        }
        for (Item item : items) {
            if (ObjectUtils.isEmpty(item.getShortDescription())) {
                throw new ReceiptValidationException("Item short description must not be empty.");
            }
            if (ObjectUtils.isEmpty(item.getPrice())) {
                throw new ReceiptValidationException("Item price must not be empty.");
            }
        }
    }
    // Currently, I am only testing empty values, but further validations can be performed to check data type and input format
}
