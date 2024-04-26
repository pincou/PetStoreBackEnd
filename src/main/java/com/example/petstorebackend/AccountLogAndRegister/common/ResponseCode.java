package com.example.petstorebackend.AccountLogAndRegister.common;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(2000,"成功"),
    ERROR(1001,"参数错误"),
    INTERFACEERROR(3000,"接口错误");

    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code=code;
        this.desc=desc;
    }
}
