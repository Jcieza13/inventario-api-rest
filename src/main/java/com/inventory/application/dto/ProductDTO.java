package com.inventory.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;


    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;


    @NotNull(message = "La cantidad no puede ser nula")
    @DecimalMin(value = "0", message = "La cantidad debe ser mayor o igual a cero")
    private Integer quantity;


    @NotNull(message = "El precio no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero")
    private Double price;

    @NotNull(message = "La categoría no puede estar vacía")
    @Valid
    private CategoryDTO category;

}