package com.duckad.kadshop.service.cartitem;

import java.util.List;

import com.duckad.kadshop.model.CartItem;

public interface ICartItemService {
    CartItem find(Long id);
    void delete(Long id);
    CartItem update(CartItem cartItem, Long id);
    List<CartItem> findAll();
    CartItem create(CartItem cartItem);
    List<CartItem> findAllByCart(Long cartId);
}
