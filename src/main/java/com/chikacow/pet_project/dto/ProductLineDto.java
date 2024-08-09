package com.chikacow.pet_project.dto;

import com.chikacow.pet_project.domain.Category;
import com.chikacow.pet_project.domain.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLineDto {
    private long id;
    private String name;

    private String description;

    private long category;

}
