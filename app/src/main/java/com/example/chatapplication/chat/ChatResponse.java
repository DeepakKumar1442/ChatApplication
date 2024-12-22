package com.example.chatapplication.chat;

public class ChatResponse {
    private String message;
    private String senderId;

    // No-argument constructor
    public ChatResponse() {
        // Default constructor required for Firebase
    }

    // Constructor with arguments
    public ChatResponse(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
