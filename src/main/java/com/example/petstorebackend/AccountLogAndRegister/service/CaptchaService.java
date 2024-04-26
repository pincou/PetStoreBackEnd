package com.example.petstorebackend.AccountLogAndRegister.service;

import jakarta.servlet.http.HttpServletRequest;

import java.awt.image.BufferedImage;
import java.util.Map;

public interface CaptchaService {
    Map<String, Object> generateCaptcha();

    boolean verifyCaptcha(String userInputCaptcha);

    String generateRandomCaptcha();

    BufferedImage generateCaptchaImage(String captchaText);

    String convertImageToBase64(BufferedImage image);

    HttpServletRequest getRequest();
}
