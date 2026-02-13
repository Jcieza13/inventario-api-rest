package com.inventory.application.mapper;

import com.inventory.application.dto.CategoryDTO;
import com.inventory.domain.model.Category;




public interface CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNameCat(category.getNameCat());
        return dto;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category entity = new Category();
        entity.setId(categoryDTO.getId());
        entity.setNameCat(categoryDTO.getNameCat());
        return entity;
    }
}


