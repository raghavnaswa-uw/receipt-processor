package com.fetchrewards.dto;

public class GetReceiptPointsResponse {
    private int points;

    public GetReceiptPointsResponse(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetReceiptPointsResponse other = (GetReceiptPointsResponse) o;
        return points == other.points;
    }
}
