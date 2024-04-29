package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;

public interface GetProductService {
    ItemDetail GetItem(String productid);
    Inventory GetInventory(String productid);
}
