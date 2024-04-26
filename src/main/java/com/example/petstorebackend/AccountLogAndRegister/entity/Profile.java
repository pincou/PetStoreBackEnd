package com.example.petstorebackend.AccountLogAndRegister.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("profile")
public class Profile {
    @TableId("userid")
    private String userid;
    @TableField("langpref")
    private String langpref;
    @TableField("favcategory")
    private String favcategory;
    @TableField("mylistopt")
    private int mylistopt;
    @TableField("banneropt")
    private int banneropt;
}
