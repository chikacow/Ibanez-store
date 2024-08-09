package com.chikacow.pet_project.dto;

import com.chikacow.pet_project.domain.Color;
import com.chikacow.pet_project.domain.ProductFeature;
import com.chikacow.pet_project.domain.ProductLine;
import jakarta.persistence.*;

import java.util.List;

public class ProductDto {

    private long id;

    private String name;

    private String description;


    private long productLineId;

    private String mainImage;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProductFeature> features;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Color> colors;

}
