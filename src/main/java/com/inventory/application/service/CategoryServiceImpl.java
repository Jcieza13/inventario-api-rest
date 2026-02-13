package com.inventory.application.service;

import com.inventory.application.dto.CategoryDTO;
import com.inventory.domain.model.Category;
import com.inventory.domain.service.CategoryService;
import com.inventory.infrastucture.exception.CustomException;
import com.inventory.infrastucture.exception.ResourceNotFoundException;
import com.inventory.infrastucture.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {

   private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Convierte un DTO CategoryDTO en una entidad Category.
     */
    private Category toEntity(CategoryDTO categoryDTO) {
        if (categoryDTO == null) {
            return null;
        }
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setNameCat(categoryDTO.getNameCat());
        return category;
    }

    /**
     * Convierte una entidad Category en un DTO CategoryDTO.
     */
    private CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNameCat(category.getNameCat());
        return dto;
    }

    /**
     * Crea una categoría en el sistema.
     */
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        System.out.println("Creating category: " + categoryDTO);
        validateCategoryData(categoryDTO);
        Category category = toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        System.out.println("Saved category: " + savedCategory);
        return toDTO(savedCategory);
    }
    @Override
    public Optional<Category> findByNameCat(String nameCat) {
        return categoryRepository.findByNameCat(nameCat);
    }

    /**
     * Valida que la categoría no esté vacía.
     */
    private void validateCategoryData(CategoryDTO categoryDTO) {
        if (categoryDTO.getNameCat() == null || categoryDTO.getNameCat().trim().isEmpty()) {
            throw new CustomException("El nombre de la categoría no puede estar vacío", 400);
        }
    }

    /**
     * Obtiene una categoría por su Id.
     */
    @Override
    @Transactional // Asegura que la sesión esté abierta durante este método
    public Category getCategoryById(Long id) {
        System.out.println("Fetching category by ID: " + id);
        return categoryRepository.findByIdWithProducts(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
    }

    /**
     * Valida que la categoría exista.
     */
    @Override
    public void validateCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CustomException("La categoría con ID " + categoryId + " no existe", 404);
        }
    }
}