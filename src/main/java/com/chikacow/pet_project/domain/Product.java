package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

//just a git test
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotEmpty(message = "Blank product name!")
    private String name;

    @NotNull
    @NotEmpty(message = "Blank product description!")
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_series_id", referencedColumnName = "id")
    //refer den name thi d dc?
    private ProductSeries productSeries;

    private String mainImage;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Feature> features = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_color",
            joinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "color_id", referencedColumnName = "id")
    )
    private List<Color> colors = new ArrayList<>();

    @Transient
    private boolean onDemand;



    //private static List<Feature> tempFeature = new ArrayList<>();

//    @Override
//    public String toString() {
//        return "tostring product";
//    }


}
