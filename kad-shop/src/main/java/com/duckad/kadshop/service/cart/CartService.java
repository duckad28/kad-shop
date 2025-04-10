package com.duckad.kadshop.service.cart;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.duckad.kadshop.exception.ResourceNotFoundException;
import com.duckad.kadshop.model.Cart;
import com.duckad.kadshop.repository.cart.CartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {
    private final CartRepository cartRepository;

    @Override
    public Cart getCart(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public void clearCart(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete, () -> {throw new ResourceNotFoundException("Cart not found");});
    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        return cart.getTotalAmount();
    }
    
}
