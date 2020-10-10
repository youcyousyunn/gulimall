package com.ycs.gulimall.service;

import com.ycs.gulimall.vo.CartItemVo;
import com.ycs.gulimall.vo.CartVo;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CartService {
    CartItemVo addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
    CartItemVo getCartItem(Long skuId);
    CartVo getCart() throws ExecutionException, InterruptedException;
    void checkItem(Long skuId, Integer check);
    void changeItemCount(Long skuId, Integer num);
    void deleteIdCartInfo(Integer skuId);
    List<CartItemVo> getUserCartItems();
}
