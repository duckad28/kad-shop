package com.duckad.kadshop.service.product;

import java.util.List;

import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.request.AddProductRequest;
import com.duckad.kadshop.request.UpdateProductRequest;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);

    Long countProductByBrandAndName(String brand, String name);
}
