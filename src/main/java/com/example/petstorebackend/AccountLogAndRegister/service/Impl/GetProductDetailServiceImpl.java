package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;
import com.example.petstorebackend.AccountLogAndRegister.persistence.InventoryMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ItemMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.GetProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductDetailServiceImpl implements GetProductDetailService {

    @Autowired
    ItemMapper itemMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public Item GetItem(String productid) {
        Item item = itemMapper.selectById(productid);
        return item;
    }

    @Override
    public Inventory GetInventory(String productid) {
        Item item = itemMapper.selectById(productid);
        Inventory inventory = inventoryMapper.selectById(item.getItemid());
        return inventory;
    }
}
