package com.duckad.kadshop.repository.cartitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckad.kadshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

}
