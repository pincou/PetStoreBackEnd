package com.example.petstorebackend.AccountLogAndRegister.Util;

import cn.hutool.captcha.LineCaptcha;
import com.example.petstorebackend.AccountLogAndRegister.entity.Captcha;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CaptchaUtil {

    @Value("${captcha.expireTime}")
    private long expireTime; // 验证码过期时间

    @Value("${captcha.width}")
    private int width; // 验证码图片宽度

    @Value("${captcha.height}")
    private int height; // 验证码图片高度

    @Value("${captcha.length}")
    private int length; // 验证码长度

    // 生成验证码
    public Captcha createCode() throws IOException {
        // 创建验证码图片
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(width, height, length, 20);
        // 生成验证码
        String code = captcha.getCode();
        // 保存验证码
        Captcha cap = new Captcha(code, expireTime);
        return cap;
    }

    private static LineCaptcha createLineCaptcha(int width, int height, int length, int i) {
    }

    // 校验验证码
    public boolean checkCode(String userCode, Captcha cap) {
        // 验证码为空
        if (cap == null || cap.isExpired()) {
            return false;
        }
        // 验证码不正确
        if (!cap.getCode().equalsIgnoreCase(userCode.trim())) {
            return false;
        }
        // 通过验证
        return true;
    }

}
