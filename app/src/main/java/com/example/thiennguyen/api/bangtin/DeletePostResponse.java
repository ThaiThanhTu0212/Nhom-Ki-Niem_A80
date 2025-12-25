package com.example.thiennguyen.api.bangtin;

import com.google.gson.annotations.SerializedName;

public class DeletePostResponse {
    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
