package com.example.navigation_view;

public class BuyerInfo {
    String name, email, buyerId;

    public BuyerInfo() {

    }

    public BuyerInfo(String name, String email, String buyerId) {
        this.name = name;
        this.email = email;
        this.buyerId = buyerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getBuyerId() {
        return buyerId;
    }
}


