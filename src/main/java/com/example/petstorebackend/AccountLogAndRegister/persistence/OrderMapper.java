package com.example.petstorebackend.AccountLogAndRegister.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petstorebackend.AccountLogAndRegister.entity.Orders;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper extends BaseMapper<Orders> {
}