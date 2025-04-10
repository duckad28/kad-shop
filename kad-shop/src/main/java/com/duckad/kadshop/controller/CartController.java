package com.duckad.kadshop.controller;

import org.springframework.web.bind.annotation.RestController;

import com.duckad.kadshop.model.Cart;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.cart.ICartService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {
    private final ICartService cartService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCarts() {
        try {
            List<Cart> carts = cartService.getAll();
            return ResponseEntity.ok(new ApiResponse("Get all cart success", carts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get all cart failed", e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            Cart cart = cartService.find(id);
            return ResponseEntity.ok(new ApiResponse("Get all cart success", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get cart failed", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable Long id, @RequestBody Cart newCart) {
        try {
            Cart cart = cartService.update(newCart, id);
            return ResponseEntity.ok(new ApiResponse("Update cart success", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update cart failed", e.getMessage()));
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> updateCart(@RequestBody Cart newCart) {
        try {
            Cart cart = cartService.create(newCart);
            return ResponseEntity.ok(new ApiResponse("Create cart success", cart));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Create cart failed", e.getMessage()));
        }
    }
}
