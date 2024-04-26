package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.Util.JwtUtil;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Account;
import com.example.petstorebackend.AccountLogAndRegister.service.AccountService;
import com.example.petstorebackend.AccountLogAndRegister.VO.AccountVO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * 登录
     * @Author Zhao
     * @param username
     * @param password
     * @return 返回所生成结果码
     */
    @PostMapping("/login")
    @ResponseBody
    public CommonResponse<String> UserLogin(
            @RequestParam("username")@NotBlank(message = "账户不能为空") String username,
            @RequestParam("password") @NotBlank(message = "密码不能为空")String password){

        CommonResponse<AccountVO> response = accountService.getAccount(username,password);
        if (response.isSuccess()){
            Map<String,Object>claims=new HashMap<>();
            claims.put("username",username);
            String token= JwtUtil.getToken(claims);
            return CommonResponse.createForSuccess(2000,
                    "登录成功",
                    token);
        }
        else{
            return  CommonResponse.createForError(1001,
                    "Invalid username or password",
                    null);
        }
    }
    /**
     * 获取账户信息
     * @Author Zhao
     * @return 返回账户数据
     */
    @GetMapping("/info")
    @ResponseBody
    public CommonResponse<AccountVO> getLoginAccountInfo(
            @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return accountService.getAccount(username);
    }
/**
 * 验证码
 * @Author Zhao
 * @return 返回所生成结果码
 */
    @GetMapping("/captcha")
    @ResponseBody
    public CommonResponse<AccountVO> SendCaptcha(){

    }
    /**
     * 验证用户名是否重复
     * @Author Zhao
     * @return 结果说明字符串
     */
    @GetMapping("/username_check")
    public CommonResponse<Boolean> UsernameCheck(
            @RequestParam("username") String username){
        CommonResponse<AccountVO> response=accountService.getAccount(username);
        if (response.isSuccess()){
            return CommonResponse.createForError(2001,"用户名已存在",false);
        }
        else {
            return CommonResponse.createForSuccess(2000,"用户名可用",true);
        }
    }
    /**
     * 注册账户
     * @Author Zhao
     * @return 返回账户信息
     */
    @PostMapping("/register")
    public CommonResponse<String> UserRegister(
            @RequestParam @Pattern(regexp = "^\\S{1,25}$") @NotBlank(message = "用户名不能为空") String username,
            @RequestParam @Pattern(regexp = "^\\S{1,25}$")  @NotBlank(message = "密码不能为空") String password){
        CommonResponse<Boolean> response=UsernameCheck(username);
        if (response.isSuccess()){
            return CommonResponse.createForSuccess(2000,
                "注册成功",
                accountService.addAccount(username, password).getData().getUsername()
            );
        }
        else {
            return CommonResponse.createForError(1001,"Invalid registration parameter");
        }

    }
    /**
     * 更新账户信息
     * @Author Zhao
     * @return 返回账户数据
     */
    @PutMapping("/update")
    public CommonResponse<AccountVO> updateAccountInfo(
            @RequestBody AccountVO accountVO,
            @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        CommonResponse<AccountVO>response= accountService.updateAccount(accountVO);
        if (response.isSuccess()){
            return CommonResponse.createForSuccess(2000,
                    "资料更新成功",
                    response.getData());
        }
        else{
            return CommonResponse.createForError(1001,
                    "Invalid update parameters",
                    null);
        }
    }

}
