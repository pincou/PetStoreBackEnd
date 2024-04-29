package com.example.petstorebackend.AccountLogAndRegister.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petstorebackend.AccountLogAndRegister.entity.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
