package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;

public interface GetProductDetailService {
    Item GetItem(String productid);

    Inventory GetInventory(String productid);
}
