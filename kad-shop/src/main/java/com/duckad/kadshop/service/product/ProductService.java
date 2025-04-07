package com.duckad.kadshop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duckad.kadshop.exception.ProductNotFoundException;
import com.duckad.kadshop.model.Category;
import com.duckad.kadshop.model.Product;
import com.duckad.kadshop.repository.CategoryRepository;
import com.duckad.kadshop.repository.product.ProductRepository;
import com.duckad.kadshop.request.AddProductRequest;
import com.duckad.kadshop.request.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request) {
        // if valid cate save new product else save new cate
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                                        .orElseGet(() -> {
                                            Category newCategory = request.getCategory();
                                            return categoryRepository.save(newCategory);
                                        });
        request.setCategory(category);
        return productRepository.save(createProduct(request, category));
    }

    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
            request.getName(),
            request.getBrand(),
            request.getInventory(),
            request.getPrice(),
            request.getDescription(),
            category
        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
            .ifPresentOrElse(productRepository::delete,
                () -> {throw new ProductNotFoundException("Product not found");});
    }

    @Override
    public Product updateProduct(UpdateProductRequest product, Long id) {
        return productRepository.findById(id)
        .map(existingProduct -> updateExistingProduct(existingProduct, product))
        .map(productRepository::save)
        .orElseThrow(() -> new ProductNotFoundException("product not found"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest updateProductRequest) {
        existingProduct.setBrand(updateProductRequest.getBrand());
        existingProduct.setName(updateProductRequest.getName());
        existingProduct.setPrice(updateProductRequest.getPrice());
        existingProduct.setDescription(updateProductRequest.getDescription());
        existingProduct.setInventory(updateProductRequest.getInventory());
        Category category = categoryRepository.findByName(updateProductRequest.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
         return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

}
