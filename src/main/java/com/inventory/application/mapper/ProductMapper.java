package com.inventory.application.mapper;

import com.inventory.application.dto.ProductDTO;
import com.inventory.domain.model.Product;


import java.util.List;
import java.util.stream.Collectors;



public interface ProductMapper {


    /**
     * Convierte una entidad Product en un DTO ProductDTO.
     *
     * @param product La entidad Product a convertir.
     * @return Un objeto ProductDTO correspondiente.
     */
    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setCategory(CategoryMapper.toDTO(product.getCategory())); // Usa el mapper manual para Category
        return dto;
    }

    /**
     * Convierte un DTO ProductDTO en una entidad Product.
     *
     * @param productDTO El DTO ProductDTO a convertir.
     * @return Una entidad Product correspondiente.
     */
    public static Product toEntity(ProductDTO productDTO) {
        if (productDTO == null) {
            return null;
        }
        Product entity = new Product();
        entity.setId(productDTO.getId());
        entity.setName(productDTO.getName());
        entity.setQuantity(productDTO.getQuantity());
        entity.setPrice(productDTO.getPrice());
        entity.setCategory(CategoryMapper.toEntity(productDTO.getCategory())); // Usa el mapper manual para Category
        return entity;
    }

    /**
     * Convierte una lista de entidades Product en una lista de DTO ProductDTO.
     *
     * @param products La lista de entidades Product a convertir.
     * @return Una lista de objetos ProductDTO correspondientes.
     */
    public static List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una lista de DTO ProductDTO en una lista de entidades Product.
     *
     * @param productDTOs La lista de DTO ProductDTO a convertir.
     * @return Una lista de entidades Product correspondientes.
     */
    public static List<Product> toEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(ProductMapper::toEntity)
                .collect(Collectors.toList());
    }
}