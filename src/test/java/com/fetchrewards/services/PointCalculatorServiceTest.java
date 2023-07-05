package com.fetchrewards.services;

import com.fetchrewards.model.Item;
import com.fetchrewards.model.Receipt;
import com.fetchrewards.services.PointCalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointCalculatorServiceTest {

    private PointCalculatorService pointCalculatorService;

    @BeforeEach
    void setUp() {
        pointCalculatorService = new PointCalculatorService();
    }

    @Test
    void testCalculatePoints_case1() {
        // Create the sample receipt
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setTotal("35.35");
        receipt.setPurchaseDate(String.valueOf(LocalDate.parse("2022-01-01")));
        receipt.setPurchaseTime(String.valueOf(LocalTime.parse("13:01")));

        Item item1 = new Item("Mountain Dew 12PK", "6.49");
        Item item2 = new Item("Emils Cheese Pizza", "12.25");
        Item item3 = new Item("Knorr Creamy Chicken", "1.26");
        Item item4 = new Item("Doritos Nacho Cheese", "3.35");
        Item item5 = new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00");

        receipt.getItems().add(item1);
        receipt.getItems().add(item2);
        receipt.getItems().add(item3);
        receipt.getItems().add(item4);
        receipt.getItems().add(item5);

        // Calculate the points
        int points = pointCalculatorService.calculatePoints(receipt);

        // Verify the result
        assertEquals(28, points);
    }

    @Test
    void testCalculatePoints_case2() {
        // Create the sample receipt
        Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setTotal("9.00");
        receipt.setPurchaseDate(String.valueOf(LocalDate.parse("2022-03-20")));
        receipt.setPurchaseTime(String.valueOf(LocalTime.parse("14:33")));

        Item item1 = new Item("Gatorade", "2.25");
        Item item2 = new Item("Gatorade", "2.25");
        Item item3 = new Item("Gatorade", "2.25");
        Item item4 = new Item("Gatorade", "2.25");

        receipt.getItems().add(item1);
        receipt.getItems().add(item2);
        receipt.getItems().add(item3);
        receipt.getItems().add(item4);

        // Calculate the points
        int points = pointCalculatorService.calculatePoints(receipt);

        // Verify the result
        assertEquals(109, points);
    }
}

