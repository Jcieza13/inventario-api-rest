package com.inventory.domain.service;

import com.inventory.application.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);

    List<ProductDTO> searchProductsByName(String name);

    List<ProductDTO> filterProductsByPriceRange(Double minPrice, Double maxPrice);

}