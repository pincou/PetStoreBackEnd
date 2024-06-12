package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Orders;
import com.example.petstorebackend.AccountLogAndRegister.persistence.OrderMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public CommonResponse addOrder(Orders order) {
        int result = orderMapper.insert(order);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("insert successfully");
        }else{
            return CommonResponse.createForError("insert error");
        }
    }

    @Override
    public CommonResponse<List<Orders>> getOrders(String username) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        List<Orders> orders = orderMapper.selectList(wrapper);
        if(orders == null){
            return CommonResponse.createForError("no order whose username is " + username);
        }
        return CommonResponse.createForSuccess(orders);
    }

    @Override
    public CommonResponse deleteOrder(String orderid) {
        QueryWrapper<Orders> wrapper = new QueryWrapper<>();
        wrapper.eq("orderid", orderid);
        int resultSet = orderMapper.delete(wrapper);
        if (resultSet != 0) {
            return CommonResponse.createForSuccess(orderid);
        } else {
           return CommonResponse.createForError(" delete failed" + orderid);
        }
    }
}