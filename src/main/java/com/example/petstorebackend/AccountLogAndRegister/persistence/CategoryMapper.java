package com.example.petstorebackend.AccountLogAndRegister.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petstorebackend.AccountLogAndRegister.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
