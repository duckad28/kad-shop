package com.duckad.kadshop.service.cart;

import java.math.BigDecimal;

import com.duckad.kadshop.model.Cart;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);
}
