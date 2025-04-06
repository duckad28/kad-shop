package com.duckad.kadshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.duckad.kadshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}
