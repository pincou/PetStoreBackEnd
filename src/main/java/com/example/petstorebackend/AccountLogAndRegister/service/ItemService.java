package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemSummary;

import java.util.List;

public interface ItemService {
    public List<ItemSummary> getItemListByPage(Integer page, Integer size, Integer supplierId);
    public ItemDetail getItemDetail(String itemId);
    public boolean updateItem(ItemDetail itemDetail,Integer supplierId);
    public boolean removeItem(String itemId);
    public List<ItemSummary> searchItems(String keyword, int page, int size);
    public boolean addItem(ItemDetail itemDetail, Integer supplierId);
}
