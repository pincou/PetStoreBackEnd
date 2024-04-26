package com.example.petstorebackend.AccountLogAndRegister.persistence;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.petstorebackend.AccountLogAndRegister.entity.Profile;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper extends BaseMapper<Profile> {
}
