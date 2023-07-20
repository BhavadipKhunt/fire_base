package com.example.firebasesample;

public class product_data {
    String id;
    String name;
    String price;
    String category;
    String imgUrl;

    public product_data(String id, String name, String price, String category, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.imgUrl = imgUrl;
    }

    public product_data() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
