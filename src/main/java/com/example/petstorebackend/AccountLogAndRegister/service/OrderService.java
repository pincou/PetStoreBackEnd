package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Orders;

import java.util.List;

public interface OrderService {
    CommonResponse addOrder(Orders order);
    CommonResponse<List<Orders>> getOrders(String username);
    CommonResponse deleteOrder(String orderid);
}