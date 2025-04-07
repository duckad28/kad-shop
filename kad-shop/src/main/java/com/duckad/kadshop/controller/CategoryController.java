package com.duckad.kadshop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.duckad.kadshop.model.Category;
import com.duckad.kadshop.response.ApiResponse;
import com.duckad.kadshop.service.category.ICategoryService;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategory() {
        try {
            List<Category> res = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Get all category", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get category failed", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategory(@PathVariable Long id) {
        try {
            // Long categoryId = Long.valueOf(id);
            Category res = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Get category", res));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Get category failed", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestParam Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Delete category success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete category failed", e.getMessage()));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> putCategory(@PathVariable Long id, @RequestBody Category category) {
        try {
            categoryService.updateCategory(category, id);
            return ResponseEntity.ok(new ApiResponse("Update category success", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Update category failed", e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> postCategory(@RequestBody Category category) {
        try {
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Create category success", newCategory));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Create category failed", e.getMessage()));
        }
    }
}
