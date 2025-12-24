package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class DonationRequest {

    @SerializedName("UserId")
    public int userId;

    @SerializedName("PostId")
    public int postId;

    @SerializedName("Amount")
    public double amount;

    @SerializedName("Message")
    public String message;

    public DonationRequest(int userId, int postId, double amount, String message) {
        this.userId = userId;
        this.postId = postId;
        this.amount = amount;
        this.message = message;
    }
}
