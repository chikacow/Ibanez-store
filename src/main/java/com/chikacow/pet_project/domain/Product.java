package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
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

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "product_line_id", referencedColumnName = "id")
    //refer den name thi d dc?
    private ProductLine productLine;

    private String mainImage;

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Feature> features = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<Color> colors = new ArrayList<>();

    @Transient
    private boolean wannaCreate;

    //private static List<Feature> tempFeature = new ArrayList<>();

//    @Override
//    public String toString() {
//        return "tostring product";
//    }


}
