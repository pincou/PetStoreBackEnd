package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.Util.JwtUtil;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Orders;
import com.example.petstorebackend.AccountLogAndRegister.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public CommonResponse addOrder(@RequestBody Orders order) {
        return orderService.addOrder(order);
    }

    @GetMapping("/getOrder")
    public CommonResponse getOrdersByName(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return orderService.getOrders(username);
    }
    @PostMapping("/deleteOrder")
    public CommonResponse deleteOrders(@RequestBody String orderid){
        return orderService.deleteOrder(orderid);
    }
}