package com.inventory.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // No hay conflicto con el 'id' de Product

    @Column(nullable = false, name = "name_cat")
    private String nameCat;

    // Relación con Productos
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    // Constructor para crear una categoría sin productos
    public Category(String name) {
        this.nameCat = name;
    }


}

