package com.inventory.domain.service;

import com.inventory.application.dto.CategoryDTO;
import com.inventory.domain.model.Category;

import java.util.Optional;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    Category getCategoryById(Long id);

    Optional<Category> findByNameCat(String nameCat);

    void validateCategoryExists(Long categoryId);



}
