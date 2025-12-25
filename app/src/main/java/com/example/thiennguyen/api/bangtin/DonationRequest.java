package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class DonationRequest {

    @SerializedName("UserId")
    private int userId;

    @SerializedName("PostId")
    private int postId;

    @SerializedName("Amount")
    private double amount;

    @SerializedName("Message")
    private String message;

    public DonationRequest(int userId, int postId, double amount, String message) {
        this.userId = userId;
        this.postId = postId;
        this.amount = amount;
        this.message = message;
    }
}
