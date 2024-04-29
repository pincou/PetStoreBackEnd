package com.example.petstorebackend.AccountLogAndRegister.VO;

public class GetProduct {
    String item_id;
    double price;
    long stock;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String itemid) {
        this.item_id = itemid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
