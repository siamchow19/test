package com.example.navigation_view;

public class AddProductHelper {
    String category;
    String subCategory;
    String name;



    String productDesc;
    String price;
    String imageUrl;
    String productId;
    String shopId;

    public AddProductHelper() {
    }

    public AddProductHelper(String category, String subCategory, String name, String productDesc, String price, String imageUrl, String productId, String shopId) {
        this.category = category;
        this.subCategory = subCategory;
        this.name = name;
        this.productDesc = productDesc;
        this.price = price;
        this.imageUrl = imageUrl;
        this.productId = productId;
        this.shopId = shopId;
    }

    public String getCategory() {
        return category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public  String getProductId(){return productId;}

    public String getShopId() {
        return shopId;
    }

    public String getName() {
        return name;
    }
}

