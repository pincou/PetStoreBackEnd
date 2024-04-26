package com.example.petstorebackend.AccountLogAndRegister.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Captcha {
    private String code; //验证码
    private LocalDateTime expireTime; //过期时间

    //构造函数
    public Captcha(String code, long expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    //判断验证码是否过期
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
