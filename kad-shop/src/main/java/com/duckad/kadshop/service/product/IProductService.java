package com.duckad.kadshop.service.product;

import java.util.List;

import com.duckad.kadshop.dto.ProductDto;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.request.AddProductRequest;
import com.duckad.kadshop.request.UpdateProductRequest;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long id);
    List<ProductDto> getAllProducts();
    List<ProductDto> getProductsByCategory(String category);
    List<ProductDto> getProductsByBrand(String brand);
    List<ProductDto> getProductsByName(String name);
    List<ProductDto> getProductsByCategoryAndBrand(String category, String brand);

    Long countProductByBrandAndName(String brand, String name);
}
