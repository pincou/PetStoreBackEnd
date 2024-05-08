package com.example.petstorebackend.AccountLogAndRegister.controller;

import com.example.petstorebackend.AccountLogAndRegister.Util.JwtUtil;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Cart;
import com.example.petstorebackend.AccountLogAndRegister.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartSerivce;

    @GetMapping
    public CommonResponse<List<Cart>> getCartByUsername(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.getCartByUsername(username);
    }

    /**
     * 判断Item是否在购物车
     * @param itemDetail
     * @param token
     * @return
     */
    @PostMapping("/check")
    public CommonResponse<Boolean> checkCart(@RequestBody ItemDetail itemDetail, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        String itemId = itemDetail.getItemId();
        return cartSerivce.checkCartByUsername(itemId,username);
    }

    /**
     *添加Item到购物车
     * @param itemDetail
     * @param token
     * @return
     */
    @PostMapping
    public CommonResponse addCart(@RequestBody ItemDetail itemDetail, @RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.addCart(itemDetail, username);
    }

    /**
     * 更新购物车
     * @param cart
     * @return
     */
    @PostMapping("/update")
    public CommonResponse updateCart(@RequestBody Cart cart){
        return cartSerivce.updateCart(cart);
    }

    /**
     * 购物车移除商品
     * @param cart
     * @return
     */
    @PostMapping("/remove")
    public CommonResponse removeCart(@RequestBody Cart cart){
        return cartSerivce.removeCart(cart);
    }

    /**
     * 清空购物车
     * @param token
     * @return
     */
    @PostMapping("/reset")
    public CommonResponse resetCart(@RequestHeader("Authorization") String token){
        String username = JwtUtil.getUsername(token);
        return cartSerivce.resetCart(username);
    }

}
