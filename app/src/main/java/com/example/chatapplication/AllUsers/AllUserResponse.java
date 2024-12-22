package com.example.chatapplication.AllUsers;



public class AllUserResponse {
    private String email;
    private String name;
    private String uid;

    // Default constructor required for calls to DataSnapshot.getValue(AllUserResponse.class)
    public AllUserResponse() {
    }

    public AllUserResponse(String email, String name, String uid) {
        this.email = email;
        this.name = name;
        this.uid = uid;
    }

    // Getters and setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

