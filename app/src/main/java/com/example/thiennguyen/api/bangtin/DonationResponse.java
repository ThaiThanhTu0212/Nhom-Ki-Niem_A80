package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class DonationResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("donationId")
    private int donationId;

    @SerializedName("amount")
    private double amount;

    public String getMessage() {
        return message;
    }

    public int getDonationId() {
        return donationId;
    }

    public double getAmount() {
        return amount;
    }
}
