package com.example.petstorebackend.AccountLogAndRegister.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("category")
public class Category {
    @TableField("catid")
    private String catid;
    @TableField("name")
    private String name;
    @TableField("descn")
    private String descn;

}
