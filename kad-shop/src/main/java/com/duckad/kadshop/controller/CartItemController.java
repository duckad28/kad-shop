package com.duckad.kadshop.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.duckad.kadshop.model.CartItem;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.cartitem.ICartItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/cartitem")
@RequiredArgsConstructor
public class CartItemController {
    private final ICartItemService cartItemService;
    
    @GetMapping("/")
    public ResponseEntity<ApiResponse> getCart(@RequestParam Long cartId, @RequestParam Long productId) {
        try {
            CartItem cartItem = cartItemService.getCartItem(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Get cart  item success", cartItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get cart item failed", e.getMessage()));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> putCartQuantity(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Update cart  item success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update cart item failed", e.getMessage()));
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> addNewCartItem(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam int quantity) {
        try {
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add cart item success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Add cart item failed", e.getMessage()));
        }
    }
}
