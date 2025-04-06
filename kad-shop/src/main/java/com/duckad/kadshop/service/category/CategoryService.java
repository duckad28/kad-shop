package com.duckad.kadshop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.duckad.kadshop.exception.AlreadyExistException;
import com.duckad.kadshop.exception.CategoryNotFoundException;
import com.duckad.kadshop.model.Category;
import com.duckad.kadshop.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category)
            .filter(c -> !categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                    .orElseThrow(() -> new AlreadyExistException("Already existing category"));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.findById(id)
                            .ifPresentOrElse(categoryRepository::delete,
                                () -> {throw new CategoryNotFoundException("Category not found");});
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(c -> {c.setName(category.getName()); return categoryRepository.save(c);})
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
