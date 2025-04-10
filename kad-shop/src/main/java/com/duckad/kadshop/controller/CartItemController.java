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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCartItems(@RequestParam(value = "cart", required = false) Long id) {
        try {
            List<CartItem> cartItems = new ArrayList<>();
            if (id == null) cartItems = cartItemService.findAll();
            else cartItems = cartItemService.findAllByCart(id);
            return ResponseEntity.ok(new ApiResponse("Get all cart item success", cartItems));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get all cart item failed", e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCartById(@PathVariable Long id) {
        try {
            CartItem cartItem = cartItemService.find(id);
            return ResponseEntity.ok(new ApiResponse("Get all cart  item success", cartItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get cart failed", e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateCart(@PathVariable Long id, @RequestBody CartItem newCartItem) {
        try {
            CartItem cartItem = cartItemService.update(newCartItem, id);
            return ResponseEntity.ok(new ApiResponse("Update cart item success", cartItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update cart item failed", e.getMessage()));
        }
    }
    
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> updateCart(@RequestBody CartItem newCartItem) {
        try {
            CartItem cartItem = cartItemService.create(newCartItem);
            return ResponseEntity.ok(new ApiResponse("Create cart item success", cartItem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Create cart item failed", e.getMessage()));
        }
    }
}
