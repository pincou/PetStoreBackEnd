package com.example.petstorebackend.AccountLogAndRegister.service;

import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Cart;

import java.util.List;

public interface CartService {
    CommonResponse<List<Cart>> getCartByUsername(String username);
    CommonResponse addCart(ItemDetail itemDetail, String username);
    CommonResponse updateCart(Cart cart);
    CommonResponse removeCart(Cart cart);
    CommonResponse resetCart(String username);
    CommonResponse<Boolean> checkCartByUsername(String itemId, String username);
}