package com.example.navigation_view;

public class ShopInfo {
    private String name, mobile, email, shopName, address, shopId;

    public ShopInfo() {
    }

    public ShopInfo(String name, String mobile, String email, String shopName, String address, String shopId) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.shopName = shopName;
        this.address = address;
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getShopName() {
        return shopName;
    }

    public String getAddress() {
        return address;
    }

    public String getShopId() {
        return shopId;
    }
}

