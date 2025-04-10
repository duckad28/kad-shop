package com.duckad.kadshop.service.cart;

import java.util.List;

import com.duckad.kadshop.model.Cart;

public interface ICartService {
    Cart find(Long id);
    void delete(Long id);
    Cart update(Cart cart, Long id);
    List<Cart> getAll();
    Cart create(Cart cart);
}
