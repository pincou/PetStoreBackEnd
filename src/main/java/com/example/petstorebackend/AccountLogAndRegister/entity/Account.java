package com.example.petstorebackend.AccountLogAndRegister.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("account")
public class Account {
    @TableId(value = "userid", type = IdType.INPUT)
    private String username;
    @TableField(value = "email")
    private String email;
    @TableField(value = "firstname")
    private String firstName;
    @TableField(value = "lastname")
    private String lastName;
    @TableField(value = "status")
    private String status;
    @TableField(value = "addr1")
    private String address1;
    @TableField(value = "addr2")
    private String address2;
    @TableField(value = "city")
    private String city;
    @TableField(value = "state")
    private String state;
    @TableField(value = "zip")
    private String zip;
    @TableField(value = "country")
    private String country;
    @TableField(value = "phone")
    private String phone;

    @TableField(exist = false)
    private Profile profile = new Profile();

    public void initProfile(Profile profile) {
        this.profile = profile;
    }
}