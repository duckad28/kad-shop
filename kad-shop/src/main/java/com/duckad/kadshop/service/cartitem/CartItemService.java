package com.duckad.kadshop.service.cartitem;

import java.util.List;

import org.springframework.stereotype.Service;

import com.duckad.kadshop.exception.ResourceNotFoundException;
import com.duckad.kadshop.model.Cart;
import com.duckad.kadshop.model.CartItem;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.repository.cartitem.CartItemRepository;
import com.duckad.kadshop.repository.product.ProductRepository;
import com.duckad.kadshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService{
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;

    @Override
    public CartItem find(Long id) {
        return cartItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }

    @Override
    public void delete(Long id) {
        cartItemRepository.findById(id).ifPresentOrElse(cartItemRepository::delete, () -> {throw new ResourceNotFoundException("Cart item not found");});
    }

    @Override
    public CartItem update(CartItem cartItem, Long id) {
        return cartItemRepository.findById(id).map(c -> cartItemRepository.save(c)).orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItem create(CartItem cartItem) {
        Product product = productRepository.findByName(cartItem.getProduct().getName()).get(0);
        cartItem.setProduct(product);
        Cart cart = cartService.find(cartItem.getCart().getId());
        cartItem.setCart(cart);
        return cartItemRepository.save(cartItem);
    }

    @Override
    public List<CartItem> findAllByCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
    
}
