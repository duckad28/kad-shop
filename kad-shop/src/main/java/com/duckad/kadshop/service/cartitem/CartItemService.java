package com.duckad.kadshop.service.cartitem;

import org.springframework.stereotype.Service;

import com.duckad.kadshop.exception.ResourceNotFoundException;
import com.duckad.kadshop.model.Cart;
import com.duckad.kadshop.model.CartItem;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.repository.cart.CartRepository;
import com.duckad.kadshop.repository.cartitem.CartItemRepository;
import com.duckad.kadshop.service.cart.ICartService;
import com.duckad.kadshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService{
    private final IProductService productService;
    private final ICartService cartService;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        /*
         * Find cart, product 
         * traverse through list cartitem
         * exist : + quantity
         * else : quantity
         */
        Cart cart = cartService.find(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream().filter(ci -> ci.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItem.setTotalPrice();
        }
        cartItemRepository.save(cartItem);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.find(cartId);
        CartItem cartItem = cart.getCartItems().stream().filter(ci -> ci.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
        cart.removeItem(cartItem);
        cartRepository.save(cart);
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity){
        Cart cart = cartService.find(cartId);
        CartItem cartItem = cart.getCartItems().stream().filter(ci -> ci.getProduct().getId().equals(productId)).findFirst().orElse(new CartItem());
        if (cartItem.getId() == null) {
            throw new ResourceNotFoundException("No item in cart");
        } else {
            cartItem.setQuantity(quantity);
            cartItem.setTotalPrice();
        }
        cartItemRepository.save(cartItem);
        cart.updateTotalAmount();
        cartRepository.save(cart);
    }

    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.find(cartId);
        return cart.getCartItems().stream().filter(ci -> ci.getProduct().getId().equals(productId)).findFirst().orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));
    }
    
    
}
