    package com.inventory.application.service.unit;

    import com.inventory.application.dto.CategoryDTO;
    import com.inventory.application.dto.ProductDTO;
    import com.inventory.application.service.ProductServiceImpl;
    import com.inventory.domain.model.Category;
    import com.inventory.domain.model.Product;
    import com.inventory.domain.service.CategoryService;
    import com.inventory.infrastucture.exception.ResourceNotFoundException;
    import com.inventory.infrastucture.repository.ProductRepository;
    import org.junit.jupiter.api.BeforeEach;
    import org.junit.jupiter.api.Test;
    import org.mockito.InjectMocks;
    import org.mockito.Mock;
    import org.mockito.MockitoAnnotations;


    import java.util.Arrays;
    import java.util.List;
    import java.util.Optional;

    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.*;

    public class ProductServiceUnitTest {

        @Mock
        private ProductRepository productRepository;

        @Mock
        private CategoryService categoryService;

        @InjectMocks
        private ProductServiceImpl productService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testCreateProduct() {
            // Arrange
            ProductDTO productDTO = new ProductDTO();
            productDTO.setName("Laptop");
            productDTO.setQuantity(10);
            productDTO.setPrice(1000.0);

            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(1L);
            categoryDTO.setNameCat("Electronics");
            productDTO.setCategory(categoryDTO);

            Category category = new Category();
            category.setId(1L);
            category.setNameCat("Electronics");

            Product product = new Product();
            product.setId(1L);
            product.setName("Laptop");
            product.setQuantity(10);
            product.setPrice(1000.0);
            product.setCategory(category);

            when(categoryService.getCategoryById(1L)).thenReturn(category);
            when(productRepository.save(any(Product.class))).thenReturn(product);

            // Act
            ProductDTO result = productService.createProduct(productDTO);

            // Assert
            assertNotNull(result);
            assertEquals("Laptop", result.getName());
            assertEquals(10, result.getQuantity());
            assertEquals(1000.0, result.getPrice());
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        void testGetAllProducts() {
            // Arrange
            Product product1 = new Product();
            product1.setId(1L);
            product1.setName("Laptop");
            product1.setQuantity(10);
            product1.setPrice(1000.0);

            Product product2 = new Product();
            product2.setId(2L);
            product2.setName("Smartphone");
            product2.setQuantity(20);
            product2.setPrice(500.0);

            when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

            // Act
            List<ProductDTO> result = productService.getAllProducts();

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Laptop", result.get(0).getName());
            assertEquals("Smartphone", result.get(1).getName());
        }

        @Test
        void testGetProductById() {
            // Arrange
            Product product = new Product();
            product.setId(1L);
            product.setName("Laptop");
            product.setQuantity(10);
            product.setPrice(1000.0);

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));

            // Act
            ProductDTO result = productService.getProductById(1L);

            // Assert
            assertNotNull(result);
            assertEquals("Laptop", result.getName());
            assertEquals(10, result.getQuantity());
            assertEquals(1000.0, result.getPrice());
        }

        @Test
        void testGetProductByIdThrowsException() {
            // Arrange
            when(productRepository.findById(1L)).thenReturn(Optional.empty());

            // Act & Assert
            ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
                productService.getProductById(1L);
            });

            assertEquals("Producto no encontrado", exception.getMessage());
        }

        @Test
        void testUpdateProduct() {
            // Arrange
            Product existingProduct = new Product();
            existingProduct.setId(1L);
            existingProduct.setName("Laptop");
            existingProduct.setQuantity(10);
            existingProduct.setPrice(1000.0);

            ProductDTO updatedProductDTO = new ProductDTO();
            updatedProductDTO.setName("Updated Laptop");
            updatedProductDTO.setQuantity(15);
            updatedProductDTO.setPrice(1200.0);

            when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
            when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

            // Act
            ProductDTO result = productService.updateProduct(1L, updatedProductDTO);

            // Assert
            assertNotNull(result);
            assertEquals("Updated Laptop", result.getName());
            assertEquals(15, result.getQuantity());
            assertEquals(1200.0, result.getPrice());
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        void testDeleteProduct() {
            // Arrange
            Product product = new Product();
            product.setId(1L);
            product.setName("Laptop");

            when(productRepository.findById(1L)).thenReturn(Optional.of(product));

            // Act
            productService.deleteProduct(1L);

            // Assert
            verify(productRepository, times(1)).delete(product);
        }
}