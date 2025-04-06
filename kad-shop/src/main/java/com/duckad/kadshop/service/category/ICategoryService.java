package com.duckad.kadshop.service.category;

import java.util.List;

import com.duckad.kadshop.model.Category;

public interface ICategoryService {
    Category addCategory(Category category);
    Category getCategoryById(Long id);
    void deleteCategoryById(Long id);
    Category updateCategory(Category category, Long id);
    List<Category> getAllCategories();
}
