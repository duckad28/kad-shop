package com.duckad.kadshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duckad.kadshop.dto.ProductDto;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.request.AddProductRequest;
import com.duckad.kadshop.request.UpdateProductRequest;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.product.IProductService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProduct(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new ApiResponse("Get product success", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get product failed", e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getProducts(@RequestParam(required = false) Map<String, String> params) {
        try {
            List<ProductDto> products = new ArrayList<>();
            if (params.size() == 0) {
                products = productService.getAllProducts();
            } else {
                String name = "", brand = "", category = "";
                
                for (Map.Entry<String, String> param : params.entrySet()) {
                    if (param.getKey().equals("name")) name = param.getValue().toLowerCase();
                    if (param.getKey().equals("brand")) brand = param.getValue().toLowerCase();
                    if (param.getKey().equals("category")) category = param.getValue().toLowerCase();
                
                }

                if (name.length() > 0) products = productService.getProductsByName(name);
                if (brand.length() > 0 && category.length() > 0) products = productService.getProductsByCategoryAndBrand(category, brand);
                else if (brand.length()> 0) products = productService.getProductsByBrand(brand);
                else if (category.length() < 0) products = productService.getProductsByCategory(category);
            }
            return ResponseEntity.ok(new ApiResponse("Get products success", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get product failed", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> postProduct(@RequestBody AddProductRequest newProduct) {
        try {
            Product product = productService.addProduct(newProduct);
            return ResponseEntity.ok(new ApiResponse("Create product success", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Create product failed", e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> putProduct(@RequestBody UpdateProductRequest newProduct, @PathVariable Long id) {
        try {
            Product product = productService.updateProduct(newProduct, id);
            return ResponseEntity.ok(new ApiResponse("Update product success", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update product failed", e.getMessage()));
        }
    }

    @PutMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(new ApiResponse("Delete product success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete product failed", e.getMessage()));
        }
    }
}
