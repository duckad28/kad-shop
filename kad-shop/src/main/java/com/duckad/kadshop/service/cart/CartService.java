package com.duckad.kadshop.service.cart;

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
    public Cart find(Long id) {
        return cartRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public void delete(Long id) {
        cartRepository.findById(id).ifPresentOrElse(cartRepository::delete, () -> {throw new ResourceNotFoundException("Cart not found");});
    }

    @Override
    public Cart update(Cart cart, Long id) {
        return cartRepository.findById(id).map(cartRepository::save).orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
    }

    @Override
    public List<Cart> getAll() {
        return cartRepository.findAll();
    }

    @Override
    public Cart create(Cart cart) {
        return cartRepository.save(cart);
    }
    
}
