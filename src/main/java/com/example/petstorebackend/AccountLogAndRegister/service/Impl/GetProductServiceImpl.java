package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.entity.Inventory;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;
import com.example.petstorebackend.AccountLogAndRegister.persistence.InventoryMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ItemMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.GetProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductServiceImpl implements GetProductService {
    @Autowired
    ItemMapper itemMapper;

    @Autowired
    InventoryMapper inventoryMapper;

    @Override
    public ItemDetail GetItem(String productid) {
        Item item = itemMapper.selectById(productid);
        ItemDetail itemDetail = new ItemDetail();
        itemDetail.setItemId(item.getItemid());
        itemDetail.setList_price(item.getListprice());
        return itemDetail;
    }

    @Override
    public Inventory GetInventory(String productid) {
        Item item = itemMapper.selectById(productid);
        return inventoryMapper.selectById(item.getItemid());
    }
}
