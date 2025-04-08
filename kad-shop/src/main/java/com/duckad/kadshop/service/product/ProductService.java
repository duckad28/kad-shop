package com.duckad.kadshop.service.product;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.duckad.kadshop.dto.ImageDto;
import com.duckad.kadshop.dto.ProductDto;
import com.duckad.kadshop.exception.ProductNotFoundException;
import com.duckad.kadshop.model.Category;
import com.duckad.kadshop.model.Image;
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
    private final ModelMapper modelMapper;

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
    public List<ProductDto> getAllProducts() {
        return convertProductDtos(productRepository.findAll());
    }

    @Override
    public List<ProductDto> getProductsByCategory(String category) {
         return convertProductDtos(productRepository.findByCategoryName(category));
    }

    @Override
    public List<ProductDto> getProductsByBrand(String brand) {
        return convertProductDtos(productRepository.findByBrand(brand));
    }

    @Override
    public List<ProductDto> getProductsByName(String name) {
        return convertProductDtos(productRepository.findByName(name));
    }

    @Override
    public List<ProductDto> getProductsByCategoryAndBrand(String category, String brand) {
        return convertProductDtos(productRepository.findByCategoryNameAndBrand(category, brand));
    }

    @Override
    public Long countProductByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    public List<ProductDto> convertProductDtos(List<Product> products) {
        return products.stream().map(this::convertProductDto).toList();
    }

    public ProductDto convertProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = product.getImages();
        List<ImageDto> imageDtos = images.stream().map(img -> modelMapper.map(img, ImageDto.class)).toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
