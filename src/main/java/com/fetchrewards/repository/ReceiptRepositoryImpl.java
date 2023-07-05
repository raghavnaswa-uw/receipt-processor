package com.fetchrewards.repository;

import com.fetchrewards.exceptions.ReceiptNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ReceiptRepositoryImpl implements ReceiptRepository {

    private final Map<String, Integer> receiptPointsStore;

    public ReceiptRepositoryImpl() {
        this.receiptPointsStore = new HashMap<>();
    }

    @Override
    public void addPointsByReceiptId(String id, int points) {
        receiptPointsStore.put(id, points);
    }

    @Override
    public int getPointsByReceiptId(String id)  throws IllegalArgumentException {
        if (receiptPointsStore.containsKey(id)) {
            return receiptPointsStore.get(id);
        } else {
            throw new IllegalArgumentException("Receipt ID not found: " + id);
        }
    }
}
