package com.example.thiennguyen.api.bangtin;

public class DonationRequest {
    private int userId;
    private int postId;
    private double amount;
    private String message;

    public DonationRequest(int userId, int postId, double amount, String message) {
        this.userId = userId;
        this.postId = postId;
        this.amount = amount;
        this.message = message;
    }

    // Getters and setters (có thể cần cho một số thư viện JSON)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
