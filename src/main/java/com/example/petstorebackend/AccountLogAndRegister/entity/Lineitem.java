package com.example.petstorebackend.AccountLogAndRegister.entity;

public class Lineitem {
    private long orderid;
    private long linenum;
    private String itemid;
    private long quantity;
    private double unitprice;


    public long getOrderid() {
        return orderid;
    }

    public void setOrderid(long orderid) {
        this.orderid = orderid;
    }


    public long getLinenum() {
        return linenum;
    }

    public void setLinenum(long linenum) {
        this.linenum = linenum;
    }


    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }


    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }


    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }
}
