package com.chikacow.pet_project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String colorCode;

    private String name;

    private String alias;

    private String image;

    @ManyToMany(mappedBy = "colors")
    private List<Product> productList = new ArrayList<>();

//    @Override
//    public String toString() {
//        return "color toString";
//    }


}
