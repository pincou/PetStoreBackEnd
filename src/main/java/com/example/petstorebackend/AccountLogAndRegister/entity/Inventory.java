package com.example.petstorebackend.AccountLogAndRegister.entity;

public class Inventory {
    private String itemid;
    private long qty;


    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }


    public long getQty() {
        return qty;
    }

    public void setQty(long qty) {
        this.qty = qty;
    }
}
