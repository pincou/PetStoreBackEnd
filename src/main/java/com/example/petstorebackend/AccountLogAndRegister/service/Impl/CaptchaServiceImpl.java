package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.example.petstorebackend.AccountLogAndRegister.service.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service("captchaService")
public class CaptchaServiceImpl implements CaptchaService {
    public static final String CAPTCHA_SESSION_KEY = "captcha";

    @Override
    public Map<String, Object> generateCaptcha() {
        String captchaText = generateRandomCaptcha();

        // 生成验证码图像
        BufferedImage captchaImage = generateCaptchaImage(captchaText);

        // 将验证码文本和图像返回给前端
        Map<String, Object> captchaData = new HashMap<>();
        captchaData.put("captchaText", captchaText);
        captchaData.put("captchaImage", convertImageToBase64(captchaImage));

        // 存储验证码文本
        HttpSession session = getRequest().getSession();
        session.setAttribute(CAPTCHA_SESSION_KEY, captchaText);

        return captchaData;
    }

    @Override
    public boolean verifyCaptcha(String userInputCaptcha) {
        HttpSession session = getRequest().getSession();
        String storedCaptcha = (String) session.getAttribute(CAPTCHA_SESSION_KEY);
        return storedCaptcha != null && storedCaptcha.equals(userInputCaptcha);
    }

    @Override
    public String generateRandomCaptcha() {
        // 定义验证码字符集合
        String captchaChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        // 定义验证码长度
        int captchaLength = 6;

        // 使用StringBuilder拼接随机生成的验证码
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < captchaLength; i++) {
            // 从字符集合中随机选择一个字符并追加到验证码中
            captcha.append(captchaChars.charAt(random.nextInt(captchaChars.length())));
        }

        // 返回生成的验证码文本
        return captcha.toString();
    }

    @Override
    public BufferedImage generateCaptchaImage(String captchaText) {
        int width = 160;  // 验证码图像的宽度
        int height = 50;  // 验证码图像的高度

        // 创建BufferedImage对象，配置图像属性
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // 设置背景颜色和绘画区域
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // 设置字体样式
        Font font = new Font("Arial", Font.BOLD, 40);
        g2d.setFont(font);

        // 添加噪点
        Random rand = new Random();
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < width * height / 10; i++) {
            int x = rand.nextInt(width);
            int y = rand.nextInt(height);
            g2d.fillRect(x, y, 1, 1);
        }

        // 添加干扰线
        for (int i = 0; i < 6; i++) {
            int x1 = rand.nextInt(width);
            int x2 = rand.nextInt(width);
            int y1 = rand.nextInt(height);
            int y2 = rand.nextInt(height);
            g2d.setColor(new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
            g2d.drawLine(x1, y1, x2, y2);
        }

        // 绘制验证码文本
        g2d.setColor(new Color(0, 100, 0));
        int x = 10;  // 文字的横坐标开始位置
        int y = 40;  // 文字的纵坐标开始位置
        for (char c : captchaText.toCharArray()) {
            // 随机生成字体颜色
            g2d.setColor(new Color(rand.nextInt(150), rand.nextInt(150), rand.nextInt(150)));
            g2d.drawString(String.valueOf(c), x, y);
            x += 30; // 每个字符宽度间隔
        }

        g2d.dispose();  // 释放图形上下文使用的系统资源

        return image;
    }

    @Override
    public String convertImageToBase64(BufferedImage image) {
        String base64Image = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(image, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();

            base64Image = Base64.getEncoder().encodeToString(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Image;
    }

    @Override
    public HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes.getRequest();
    }
}
