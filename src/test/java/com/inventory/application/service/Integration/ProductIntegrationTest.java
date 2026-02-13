package com.inventory.application.service.Integration;

import com.inventory.application.dto.CategoryDTO;
import com.inventory.application.dto.ProductDTO;
import com.inventory.application.service.ProductServiceImpl;
import com.inventory.domain.model.Category;
import com.inventory.domain.model.Product;
import com.inventory.domain.service.CategoryService;
import com.inventory.infrastucture.exception.ResourceNotFoundException;
import com.inventory.infrastucture.repository.CategoryRepository;
import com.inventory.infrastucture.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest  // Carga el contexto de Spring
@ActiveProfiles("test") // Usa el perfil de prueba (H2)
public class ProductIntegrationTest {

    @Autowired
    private ProductServiceImpl productServiceImpl; // Servicio real

    @Autowired
    private ProductRepository productRepository; // Repositorio real

    @Autowired
    private CategoryService categoryService; // Servicio real

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        // Limpia la base de datos antes de cada prueba
        productRepository.deleteAll();
        categoryRepository.deleteAll();
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

    @Test
    void testCreateProduct() {
        // Arrange
        System.out.println("Starting testCreateProduct...");
        CategoryDTO categoryDTO = new CategoryDTO(null, "Electrónica");
        ProductDTO productDTO = new ProductDTO(null, "Laptop", 10, 999.99, categoryDTO);
        System.out.println("Creating product: " + productDTO);
        // Act
        ProductDTO createdProduct = productServiceImpl.createProduct(productDTO);
        System.out.println("Created product: " + createdProduct);

        // Assert
        assertNotNull(createdProduct.getId());
        assertEquals("Laptop", createdProduct.getName());
        assertEquals(10, createdProduct.getQuantity());
        assertEquals(999.99, createdProduct.getPrice());
        assertNotNull(createdProduct.getCategory());
        assertEquals("Electrónica", createdProduct.getCategory().getNameCat());
    }

    @Test
    void testGetProductById() {
        // Arrange
        CategoryDTO savedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Electrónica"));
        Category savedCategory = toEntity(savedCategoryDTO); // Usamos el método manual
        Product product = new Product(null, "Laptop", 10, 999.99, savedCategory);
        Product savedProduct = productRepository.save(product);

        // Act
        ProductDTO productDTO = productServiceImpl.getProductById(savedProduct.getId());

        // Assert
        assertNotNull(productDTO);
        assertEquals(savedProduct.getId(), productDTO.getId());
        assertEquals("Laptop", productDTO.getName());
        assertEquals(10, productDTO.getQuantity());
        assertEquals(999.99, productDTO.getPrice());
        assertEquals("Electrónica", productDTO.getCategory().getNameCat());
    }

    @Test
    void testUpdateProduct() {
        // Arrange: Crear una categoría inicial
        CategoryDTO savedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Electrónica"));

        // Crear un producto inicial
        ProductDTO productDTO = new ProductDTO(null, "Laptop", 10, 999.99, savedCategoryDTO);
        ProductDTO savedProductDTO = productServiceImpl.createProduct(productDTO);

        // Crear una nueva categoría para actualizar
        CategoryDTO updatedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Ropa"));

        // Crear un DTO para el producto actualizado
        ProductDTO updatedProductDTO = new ProductDTO(
                savedProductDTO.getId(), // ID del producto existente
                "Smartphone",            // Nuevo nombre
                5,                       // Nueva cantidad
                499.99,                  // Nuevo precio
                updatedCategoryDTO       // Nueva categoría
        );

        // Act: Actualizar el producto
        ProductDTO result = productServiceImpl.updateProduct(savedProductDTO.getId(), updatedProductDTO);

        // Assert: Verificar que el producto se actualizó correctamente
        assertNotNull(result);
        assertEquals("Smartphone", result.getName());
        assertEquals(5, result.getQuantity());
        assertEquals(499.99, result.getPrice());
        assertEquals("Ropa", result.getCategory().getNameCat());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        CategoryDTO savedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Electrónica"));
        Category savedCategory = toEntity(savedCategoryDTO); // Usamos el método manual
        Product product = new Product(null, "Laptop", 10, 999.99, savedCategory);
        Product savedProduct = productRepository.save(product);

        // Act
        productServiceImpl.deleteProduct(savedProduct.getId());

        // Assert
        assertFalse(productRepository.findById(savedProduct.getId()).isPresent());
    }

    @Test
    void testSearchProductsByName() {
        // Arrange
        CategoryDTO savedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Electrónica"));
        Category savedCategory = toEntity(savedCategoryDTO); // Usamos el método manual
        Product product1 = new Product(null, "Laptop", 10, 999.99, savedCategory);
        Product product2 = new Product(null, "Laptop Gaming", 5, 1499.99, savedCategory);
        productRepository.saveAll(List.of(product1, product2));

        // Act
        List<ProductDTO> result = productServiceImpl.searchProductsByName("Laptop");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Laptop Gaming", result.get(1).getName());
    }

    @Test
    void testSearchProductsByName_NoResults() {
        // Act
        List<ProductDTO> result = productServiceImpl.searchProductsByName("Tablet");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        CategoryDTO savedCategoryDTO = categoryService.createCategory(new CategoryDTO(null, "Electrónica"));
        Category savedCategory = toEntity(savedCategoryDTO); // Usamos el método manual
        Product product1 = new Product(null, "Laptop", 10, 999.99, savedCategory);
        Product product2 = new Product(null, "Smartphone", 5, 499.99, savedCategory);
        productRepository.saveAll(List.of(product1, product2));

        // Act
        List<ProductDTO> result = productServiceImpl.getAllProducts();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Smartphone", result.get(1).getName());
    }

    @Test
    void testGetProductByIdThrowsException() {
        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            productServiceImpl.getProductById(999L); // ID inexistente
        });
        assertEquals("Producto no encontrado", exception.getMessage());
    }
}