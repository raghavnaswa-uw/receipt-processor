package com.fetchrewards.repository;

import com.fetchrewards.exceptions.ReceiptNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository {
    void addPointsByReceiptId(String id, int points);
    int getPointsByReceiptId(String id) throws IllegalArgumentException;
}

