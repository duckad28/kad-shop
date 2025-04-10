package com.duckad.kadshop.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckad.kadshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
