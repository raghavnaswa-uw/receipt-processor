package com.fetchrewards.dto;

public class ProcessReceiptResponse {
    private String id;

    public ProcessReceiptResponse(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessReceiptResponse other = (ProcessReceiptResponse) o;
        return id == other.id;
    }
}
