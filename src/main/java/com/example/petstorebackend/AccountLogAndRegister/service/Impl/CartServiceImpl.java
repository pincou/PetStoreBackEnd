package com.example.petstorebackend.AccountLogAndRegister.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.petstorebackend.AccountLogAndRegister.VO.ItemDetail;
import com.example.petstorebackend.AccountLogAndRegister.common.CommonResponse;
import com.example.petstorebackend.AccountLogAndRegister.entity.Cart;
import com.example.petstorebackend.AccountLogAndRegister.entity.ItemInventory;
import com.example.petstorebackend.AccountLogAndRegister.persistence.CartMapper;
import com.example.petstorebackend.AccountLogAndRegister.persistence.ItemInventoryMapper;
import com.example.petstorebackend.AccountLogAndRegister.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartMapper cartMapper;
    @Autowired
    ItemInventoryMapper itemInventoryMapper;
    @Override
    public CommonResponse<List<Cart>> getCartByUsername(String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        List<Cart> carts = cartMapper.selectList(wrapper);
        if(carts == null){
            return CommonResponse.createForError("no cart whose username is " + username);
        }
        return CommonResponse.createForSuccess(carts);
    }

    @Override
    public CommonResponse addCart(ItemDetail itemDetail, String username) {
        ItemInventory itemInventory = itemInventoryMapper.selectById(itemDetail.getItemId());
        System.out.println(itemInventory);
        if(itemInventory.getQuantity() == 0){
            return CommonResponse.createForError(itemDetail.getItemId()+" inventory sell out");
        }else{
            itemInventory.setQuantity(itemInventory.getQuantity() - 1);
            itemInventoryMapper.updateById(itemInventory);
        }
        Cart cart = new Cart();

        cart.setNumber(1);
        cart.setUserId(username);
        cart.setProductId(itemDetail.getProduct());
        cart.setItemId(itemDetail.getItemId());
        int result = cartMapper.insert(cart);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("insert success");
        }else{
            return CommonResponse.createForError("insert error");
        }
    }

    @Override
    public CommonResponse updateCart(Cart cart) {
        ItemInventory itemInventory = itemInventoryMapper.selectById(cart.getItemId());
        if(itemInventory.getQuantity() == 0){
            return CommonResponse.createForError(cart.getItemId()+" inventory sell out");
        }else{
            itemInventory.setQuantity(itemInventory.getQuantity() - 1);
            itemInventoryMapper.updateById(itemInventory);
        }
        int result = cartMapper.updateById(cart);
        if(result == 1){
            return CommonResponse.createForSuccessMessage("update cart success");
        }else{
            return CommonResponse.createForError("update cart error");
        }
    }

    @Override
    public CommonResponse removeCart(Cart cart){
        int result = cartMapper.deleteById(cart);
        ItemInventory itemInventory = itemInventoryMapper.selectById(cart.getItemId());
        itemInventory.setQuantity(itemInventory.getQuantity() + cart.getNumber());
        itemInventoryMapper.updateById(itemInventory);
        if(result == 0){
            return CommonResponse.createForError("delete cart error");
        }else{
            return CommonResponse.createForSuccessMessage("delete cart success");
        }
    }

    @Override
    public CommonResponse resetCart(String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        int result = cartMapper.delete(wrapper);
        return CommonResponse.createForSuccess("reset cart ok");
    }

    @Override
    public CommonResponse<Boolean> checkCartByUsername(String itemId, String username) {
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("userid", username);
        wrapper.eq("itemid", itemId);
        return  CommonResponse.createForSuccess(cartMapper.selectList(wrapper).isEmpty());
    }

}
