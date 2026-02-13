package com.inventory.application.service;

import com.inventory.application.dto.CategoryDTO;
import com.inventory.application.dto.ProductDTO;
import com.inventory.domain.model.Category;
import com.inventory.domain.model.Product;
import com.inventory.domain.service.CategoryService;
import com.inventory.domain.service.ProductService;
import com.inventory.infrastucture.exception.CustomException;
import com.inventory.infrastucture.exception.ResourceNotFoundException;
import com.inventory.infrastucture.repository.ProductRepository;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    /**
     * Convierte un DTO ProductDTO en una entidad Product.
     */
    private Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setQuantity(productDTO.getQuantity());
        product.setPrice(productDTO.getPrice());
        // Convertir la categoría manualmente
        product.setCategory(toCategoryEntity(productDTO.getCategory()));
        return product;
    }

    /**
     * Convierte una entidad Product en un DTO ProductDTO.
     */
    private ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        // Convertir la categoría manualmente
        dto.setCategory(toCategoryDTO(product.getCategory()));
        return dto;
    }

    /**
     * Convierte un DTO CategoryDTO en una entidad Category.
     */
    private Category toCategoryEntity(CategoryDTO categoryDTO) {
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
    private CategoryDTO toCategoryDTO(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setNameCat(category.getNameCat());
        return dto;
    }

    /**
     * Crea un nuevo producto en el sistema.
     */
    @Override
     // Asegura que la sesión esté abierta durante este método
    public ProductDTO createProduct(ProductDTO productDTO) {
        System.out.println("Creating product: " + productDTO);
        validateProductData(productDTO);

        // Buscar o crear la categoría usando CategoryService
        Category category;
        if (productDTO.getCategory().getId() != null) {
            // Si el ID de la categoría existe, buscarla en la base de datos
            category = categoryService.getCategoryById(productDTO.getCategory().getId());
        } else {
            // Si el ID es nulo, crear una nueva categoría
            CategoryDTO createdCategoryDTO = categoryService.createCategory(productDTO.getCategory());
            category = toCategoryEntity(createdCategoryDTO);
        }

        // Convertir DTO a Entidad manualmente
        Product product = toEntity(productDTO);
        product.setCategory(category);

        // Guardar el producto
        Product savedProduct = productRepository.save(product);

        // Imprime el DTO en lugar de la entidad completa
        System.out.println("Saved product DTO: " + toDTO(savedProduct));
        return toDTO(savedProduct);
    }

    /**
     * Obtiene todos los productos del sistema.
     */
    @Override
    @Transactional
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un producto por su ID.
     */
    @Override
    @Transactional
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        return toDTO(product);
    }

    /**
     * Busca productos por nombre.
     */
    @Override
    @Transactional
    public List<ProductDTO> searchProductsByName(String name) {
        List<Product> products = productRepository.findByNameContainingIgnoreCase(name);
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Actualiza un producto existente.
     */
    @Override
    @Transactional // Asegura que la sesión esté abierta durante este método
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

        // Actualizar los campos del producto
        existingProduct.setName(productDTO.getName());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setPrice(productDTO.getPrice());

        // Actualizar la categoría si es necesario
        if (productDTO.getCategory() != null) {
            Category category;
            if (productDTO.getCategory().getId() != null) {
                // Si se proporciona un ID de categoría, buscarla en la base de datos
                category = categoryService.getCategoryById(productDTO.getCategory().getId());
                // Actualizar el nameCat si se proporciona un nuevo valor
                if (productDTO.getCategory().getNameCat() != null) {
                    category.setNameCat(productDTO.getCategory().getNameCat());
                }
            } else {
                // Si no se proporciona un ID, buscar o crear una nueva categoría
                Optional<Category> optionalCategory = categoryService.findByNameCat(productDTO.getCategory().getNameCat());
                if (optionalCategory.isPresent()) {
                    category = optionalCategory.get();
                } else {
                    CategoryDTO createdCategoryDTO = categoryService.createCategory(productDTO.getCategory());
                    category = toCategoryEntity(createdCategoryDTO);
                }
            }
            existingProduct.setCategory(category);
        }

        // Guardar el producto actualizado
        Product updatedProduct = productRepository.save(existingProduct);

        // Convertir Entidad a DTO manualmente
        return toDTO(updatedProduct);
    }

    /**
     * Elimina un producto por su ID.
     */
    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));
        productRepository.delete(product);
    }

    /**
     * Filtra un producto por su rango de precios.
     */
    @Override
    public List<ProductDTO> filterProductsByPriceRange(Double minPrice, Double maxPrice) {
        if (minPrice != null && maxPrice != null && minPrice > maxPrice) {
            throw new CustomException("El precio mínimo no puede ser mayor que el precio máximo", 400);
        }
        List<Product> products = productRepository.findByPriceRange(minPrice, maxPrice);
        return products.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Valída los datos del producto.
     */
    private void validateProductData(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new CustomException("El nombre no puede estar vacío", 400);
        }
        if (productDTO.getQuantity() == null || productDTO.getQuantity() < 0) {
            throw new CustomException("La cantidad debe ser mayor o igual a cero", 400);
        }
        if (productDTO.getPrice() == null || productDTO.getPrice() <= 0) {
            throw new CustomException("El precio debe ser mayor que cero", 400);
        }
        if (productDTO.getCategory() == null || productDTO.getCategory().getNameCat() == null || productDTO.getCategory().getNameCat().trim().isEmpty()) {
            throw new CustomException("El nombre de la categoría no puede estar vacío", 400);
        }
        if (productDTO.getCategory().getId() != null) {
            categoryService.validateCategoryExists(productDTO.getCategory().getId());
        }
    }
}