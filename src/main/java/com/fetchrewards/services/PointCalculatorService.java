package com.fetchrewards.services;

import com.fetchrewards.model.Item;
import com.fetchrewards.model.Receipt;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class PointCalculatorService {

    /**
     * Calculates the total points awarded for a receipt based on defined rules.
     *
     * @param receipt The receipt for which to calculate the points.
     * @return The total points awarded for the receipt.
     */
    public int calculatePoints(Receipt receipt) {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += countAlphanumericCharacters(receipt.getRetailer());

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (isRoundDollarAmount(receipt.getTotal())) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (isMultipleOfQuarter(receipt.getTotal())) {
            points += 25;
        }

        // Rule 4: 5 points for every two items on the receipt
        int numItems = receipt.getItems().size();
        points += (numItems / 2) * 5;

        // Rule 5: Multiply the price by 0.2 and round up to the nearest integer if the trimmed length of the item
        // description is a multiple of 3
        for (Item item : receipt.getItems()) {
            int trimmedLength = item.getShortDescription().trim().length();
            if (trimmedLength % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                int itemPoints = (int) Math.ceil(price * 0.2);
                points += itemPoints;
            }
        }

        // Rule 6: 6 points if the day in the purchase date is odd
        int purchaseDay = getDayFromDate(receipt.getPurchaseDate());
        if (purchaseDay % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (isTimeBetween(receipt.getPurchaseTime(), "14:00", "16:00")) {
            points += 10;
        }

        return points;
    }

    private int countAlphanumericCharacters(String input) {
        return (int) input.chars().filter(Character::isLetterOrDigit).count();
    }

    private boolean isRoundDollarAmount(String amount) {
        double total = Double.parseDouble(amount);
        return total == Math.floor(total);
    }

    private boolean isMultipleOfQuarter(String amount) {
        double total = Double.parseDouble(amount);
        return total % 0.25 == 0;
    }

    private int getDayFromDate(String date) {
        LocalDate localDate = LocalDate.parse(date);
        return localDate.getDayOfMonth();
    }

    private boolean isTimeBetween(String time, String startTime, String endTime) {
        LocalTime localTime = LocalTime.parse(time);
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        return localTime.isAfter(start) && localTime.isBefore(end);
    }
}
