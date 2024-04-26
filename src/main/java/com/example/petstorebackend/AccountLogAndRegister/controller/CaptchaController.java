package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/captcha")
public class CaptchaController {
    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/generate")
    public Map<String, Object> generateCaptcha() {
        return captchaService.generateCaptcha();
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCaptcha(@RequestBody Map<String, String> requestBody) {
        String userInputCaptcha = requestBody.get("captcha");
        boolean isValid = captchaService.verifyCaptcha(userInputCaptcha);
        if (isValid) {
            return ResponseEntity.ok("Captcha verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid captcha");
        }
    }
}
