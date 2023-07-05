package com.fetchrewards.services;

import com.fetchrewards.exceptions.ReceiptValidationException;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.model.Item;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class ReceiptValidationServiceTest {

    private ReceiptValidationService receiptValidationService;

    @BeforeEach
    public void setup() {
        receiptValidationService = new ReceiptValidationService();
    }

    @Test
    public void testValidateReceipt_withValidReceipt() {
        // Create a valid receipt
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertDoesNotThrow(() -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withNullReceipt() {
        // Validate a null receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(null));
    }

    @Test
    public void testValidateReceipt_withEmptyRetailer() {
        // Create a receipt with an empty retailer
        Receipt receipt = new Receipt();
        receipt.setRetailer("");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyPurchaseDate() {
        // Create a receipt with an empty purchase date
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyPurchaseTime() {
        // Create a receipt with an empty purchase time
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyTotal() {
        // Create a receipt with an empty total
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyItemsList() {
        // Create a receipt with an empty items list
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyItemShortDescription() {
        // Create a receipt with an item having an empty short description
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("");
        item1.setPrice("2.25");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

    @Test
    public void testValidateReceipt_withEmptyItemPrice() {
        // Create a receipt with an item having an empty price
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Gatorade");
        item1.setPrice("");
        items.add(item1);
        receipt.setItems(items);
        receipt.setTotal("2.25");

        // Validate the receipt
        Assertions.assertThrows(ReceiptValidationException.class, () -> receiptValidationService.validateReceipt(receipt));
    }

}
