package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSeries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    private String name;

//    @NotNull(message = "Description cannot be null")
//    @NotEmpty(message = "Description cannot be empty")
    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "product_line_id", referencedColumnName = "id")
    private ProductLine productLine;

    @OneToMany(mappedBy = "productSeries", orphanRemoval = false, cascade = CascadeType.ALL)
    private List<Product> productList;


    @Override
    public String toString() {
        return "hi product series";
    }
}
