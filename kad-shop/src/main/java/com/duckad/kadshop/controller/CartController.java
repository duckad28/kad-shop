package com.duckad.kadshop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.duckad.kadshop.model.Cart;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final ICartService cartService;
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.getCart(id);
            return ResponseEntity.ok(new ApiResponse("Get cart success", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get cart failed", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/clear")
    public ResponseEntity<ApiResponse> deleteCartById(@PathVariable Long id) {
        try {
            cartService.clearCart(id);
            return ResponseEntity.ok(new ApiResponse("delete cart success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("delete cart failed", e.getMessage()));
        }
    }


}
